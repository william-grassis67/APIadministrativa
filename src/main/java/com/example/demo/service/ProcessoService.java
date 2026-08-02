package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ProcessoDTO;
import com.example.demo.dto.ProcessoHistoricoDTO;
import com.example.demo.dto.ProcessoMensagemDTO;
import com.example.demo.entity.Processo;
import com.example.demo.entity.ProcessoHistorico;
import com.example.demo.entity.ProcessoMensagem;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.CampoInvalidoexception;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ProcessoHistoricoRepository;
import com.example.demo.repository.ProcessoMensagemRepository;
import com.example.demo.repository.ProcessoRepository;

@Service
@Transactional
public class ProcessoService {

    private final ProcessoRepository processoRepository;
    private final ClienteRepository clienteRepository;
    private final ProcessoHistoricoRepository historicoRepository;
    private final ProcessoMensagemRepository mensagemRepository;

    public ProcessoService(
            ProcessoRepository processoRepository,
            ClienteRepository clienteRepository,
            ProcessoHistoricoRepository historicoRepository,
            ProcessoMensagemRepository mensagemRepository
    ) {
        this.processoRepository = processoRepository;
        this.clienteRepository = clienteRepository;
        this.historicoRepository = historicoRepository;
        this.mensagemRepository = mensagemRepository;
    }

    // LISTAR TODOS OS PROCESSOS
    @Transactional(readOnly = true)
    public List<ProcessoDTO> listarProcessos() {
        return processoRepository.findAll()
                .stream()
                .map(this::mapToProcessoDTO)
                .toList();
    }

    // BUSCAR PROCESSO POR ID
    @Transactional(readOnly = true)
    public ProcessoDTO buscarPorId(Integer id) {
        Processo processo = buscarProcessoEntidadePorId(id);
        return mapToProcessoDTO(processo);
    }

    // BUSCAR PROCESSOS DE UM USUÁRIO
    @Transactional(readOnly = true)
    public List<ProcessoDTO> buscarProcessosPorUsuario(Integer usuarioId) {
        if (usuarioId == null) {
            throw new CampoInvalidoexception("ID do usuário é obrigatório!");
        }

        Usuario usuario = clienteRepository.findById(usuarioId)
                .orElseThrow(() -> new CampoInvalidoexception("Usuário não encontrado!"));

        List<Processo> processos = processoRepository.findAllByUsuarioId(usuario.getId());

        if (processos.isEmpty()) {
            throw new CampoInvalidoexception("Nenhum processo encontrado para este usuário!");
        }

        return processos.stream()
                .map(this::mapToProcessoDTO)
                .toList();
    }

    // ATUALIZAR PROCESSO
    public ProcessoDTO atualizarProcesso(Integer id, ProcessoDTO dto) {
        if (id == null) {
            throw new CampoInvalidoexception("ID do processo é obrigatório para atualização!");
        }
        if (dto == null) {
            throw new CampoInvalidoexception("Os dados do processo não podem ser nulos!");
        }

        Processo processo = buscarProcessoEntidadePorId(id);

        // Atualização dos campos permitidos
        if (dto.getTipo() != null) {
            processo.setTipo(dto.getTipo());
        }
        if (dto.getStatus() != null) {
            processo.setStatus(dto.getStatus());
        }
        if (dto.getBiometriaRealizada() != null) {
            processo.setBiometriaRealizada(dto.getBiometriaRealizada());
        }
        if (dto.getDataConclusao() != null) {
            processo.setDataConclusao(dto.getDataConclusao());
        }

        // Se houver alteração do usuário vinculado
        if (dto.getUsuarioId() != null) {
            Usuario usuario = clienteRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new CampoInvalidoexception("Usuário informado não foi encontrado!"));
            processo.setUsuario(usuario);
        }

        Processo atualizado = processoRepository.save(processo);
        return mapToProcessoDTO(atualizado);
    }

    public ProcessoHistoricoDTO adicionarHistorico(Integer processoId, ProcessoHistoricoDTO dto, Integer administradorId) {
        Processo processo = buscarProcessoEntidadePorId(processoId);
        Usuario administrador = buscarAdministrador(administradorId);

        if (dto == null || dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new CampoInvalidoexception("Título do histórico é obrigatório!");
        }

        ProcessoHistorico historico = new ProcessoHistorico();
        historico.setTitulo(dto.getTitulo());
        historico.setDescricao(dto.getDescricao());
        historico.setDataHora(dto.getDataHora() != null ? dto.getDataHora() : LocalDateTime.now());
        historico.setProcesso(processo);
        historico.setAdministrador(administrador);

        processo.setUltimaAtualizacao(historico.getDataHora());
        processo.setNotificacoesNaoLidas(processo.getNotificacoesNaoLidas() + 1);
        processo.getHistorico().add(historico);

        historicoRepository.save(historico);
        processoRepository.save(processo);

        return mapToHistoricoDTO(historico);
    }

    @Transactional(readOnly = true)
    public List<ProcessoHistoricoDTO> listarHistorico(Integer processoId) {
        Processo processo = buscarProcessoEntidadePorId(processoId);
        return historicoRepository.findAllByProcessoOrderByDataHoraAsc(processo)
                .stream()
                .map(this::mapToHistoricoDTO)
                .collect(Collectors.toList());
    }

    public ProcessoMensagemDTO adicionarMensagem(Integer processoId, ProcessoMensagemDTO dto, Integer administradorId) {
        Processo processo = buscarProcessoEntidadePorId(processoId);
        Usuario administrador = buscarAdministrador(administradorId);

        if (dto == null || dto.getTexto() == null || dto.getTexto().isBlank()) {
            throw new CampoInvalidoexception("Texto da mensagem é obrigatório!");
        }

        ProcessoMensagem mensagem = new ProcessoMensagem();
        mensagem.setTexto(dto.getTexto());
        mensagem.setDataHora(dto.getDataHora() != null ? dto.getDataHora() : LocalDateTime.now());
        mensagem.setProcesso(processo);
        mensagem.setAdministrador(administrador);
        mensagem.setStatus(ProcessoMensagem.StatusMensagem.NAO_LIDA);

        processo.getMensagens().add(mensagem);
        processo.setNotificacoesNaoLidas(processo.getNotificacoesNaoLidas() + 1);
        processo.setUltimaAtualizacao(mensagem.getDataHora());

        mensagemRepository.save(mensagem);
        processoRepository.save(processo);

        return mapToMensagemDTO(mensagem);
    }

    @Transactional(readOnly = true)
    public List<ProcessoMensagemDTO> listarMensagens(Integer processoId) {
        Processo processo = buscarProcessoEntidadePorId(processoId);
        return mensagemRepository.findAllByProcessoOrderByDataHoraAsc(processo)
                .stream()
                .map(this::mapToMensagemDTO)
                .sorted(Comparator.comparing(ProcessoMensagemDTO::getDataHora))
                .collect(Collectors.toList());
    }

    public void marcarMensagensComoLidas(Integer processoId) {
        Processo processo = buscarProcessoEntidadePorId(processoId);
        List<ProcessoMensagem> mensagens = mensagemRepository.findAllByProcessoOrderByDataHoraAsc(processo);
        mensagens.forEach(mensagem -> mensagem.setStatus(ProcessoMensagem.StatusMensagem.LIDA));
        mensagemRepository.saveAll(mensagens);
        processo.setNotificacoesNaoLidas(0);
        processoRepository.save(processo);
    }

    public ProcessoDTO atualizarStatusProcesso(Integer processoId, Processo.StatusProcesso novoStatus, Integer administradorId) {
        Processo processo = buscarProcessoEntidadePorId(processoId);
        buscarAdministrador(administradorId);

        processo.setStatus(novoStatus);
        processo.setUltimaAtualizacao(LocalDateTime.now());
        processo.setNotificacoesNaoLidas(processo.getNotificacoesNaoLidas() + 1);

        Processo atualizado = processoRepository.save(processo);
        return mapToProcessoDTO(atualizado);
    }

    private Usuario buscarAdministrador(Integer administradorId) {
        if (administradorId == null) {
            throw new CampoInvalidoexception("ID do administrador é obrigatório!");
        }
        return clienteRepository.findById(administradorId)
                .orElseThrow(() -> new CampoInvalidoexception("Administrador não encontrado!"));
    }

    private Processo buscarProcessoEntidadePorId(Integer id) {
        if (id == null) {
            throw new CampoInvalidoexception("ID do processo não pode ser nulo!");
        }
        return processoRepository.findById(id)
                .orElseThrow(() -> new CampoInvalidoexception("Processo não encontrado com o ID: " + id));
    }

    private ProcessoDTO mapToProcessoDTO(Processo p) {
        if (p == null) {
            return null;
        }

        return new ProcessoDTO(
                p.getId(),
                p.getTipo(),
                p.getStatus(),
                p.getBiometriaRealizada(),
                p.getDataCriacao(),
                p.getDataConclusao(),
                p.getUsuario() != null ? p.getUsuario().getId() : null,
                p.getUsuario() != null ? p.getUsuario().getNome() : null
        );
    }

    private ProcessoHistoricoDTO mapToHistoricoDTO(ProcessoHistorico historico) {
        if (historico == null) {
            return null;
        }

        return new ProcessoHistoricoDTO(
                historico.getId(),
                historico.getTitulo(),
                historico.getDescricao(),
                historico.getDataHora(),
                historico.getAdministrador() != null ? historico.getAdministrador().getId() : null,
                historico.getAdministrador() != null ? historico.getAdministrador().getNome() : null
        );
    }

    private ProcessoMensagemDTO mapToMensagemDTO(ProcessoMensagem mensagem) {
        if (mensagem == null) {
            return null;
        }

        return new ProcessoMensagemDTO(
                mensagem.getId(),
                mensagem.getTexto(),
                mensagem.getDataHora(),
                mensagem.getAdministrador() != null ? mensagem.getAdministrador().getId() : null,
                mensagem.getAdministrador() != null ? mensagem.getAdministrador().getNome() : null,
                mensagem.getStatus()
        );
    }
}