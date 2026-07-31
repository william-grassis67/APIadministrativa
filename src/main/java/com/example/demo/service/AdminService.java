package com.example.demo.service;

import com.example.demo.dto.GuiaInssDTO;
import com.example.demo.dto.ProcessoDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.GuiasInss;
import com.example.demo.entity.Processo;
import com.example.demo.entity.Processo.StatusProcesso;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.CampoInvalidoexception;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.GuiasInssRepository;
import com.example.demo.repository.ProcessoRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminService {

    private final ClienteRepository clienteRepository;
    private final GuiasInssRepository guiasInssRepository;
    private final ProcessoRepository processoRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(
            ClienteRepository clienteRepository,
            GuiasInssRepository guiasInssRepository,
            ProcessoRepository processoRepository,
            PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.guiasInssRepository = guiasInssRepository;
        this.processoRepository = processoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CADASTRAR CLIENTE
    public UsuarioDTO registerUser(
            String nome,
            String email,
            String endereco,
            String cpf,
            String senha,
            String numeroTelefone) {

        if (nome == null || nome.isBlank()) {
            throw new CampoInvalidoexception("Nome é obrigatório!");
        }

        if (email == null || !email.contains("@")) {
            throw new CampoInvalidoexception("E-mail inválido!");
        }

        if (clienteRepository.findByEmail(email).isPresent()) {
            throw new CampoInvalidoexception("Email já cadastrado!");
        }

        if (cpf == null || cpf.replaceAll("\\D", "").length() != 11) {
            throw new CampoInvalidoexception("CPF inválido! Deve conter 11 dígitos.");
        }

        if (clienteRepository.findByCpf(cpf).isPresent()) {
            throw new CampoInvalidoexception("CPF já cadastrado!");
        }

        if (senha == null || senha.isBlank()) {
            throw new CampoInvalidoexception("Senha é obrigatória!");
        }

        if (numeroTelefone == null || numeroTelefone.replaceAll("\\D", "").length() != 11) {
            throw new CampoInvalidoexception("O número de telefone precisa ter 11 dígitos com DDD.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setEndereco(endereco);
        usuario.setCpf(cpf);
        // Criptografa a senha antes de persistir no BD
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setNumeroTelefone(numeroTelefone);
        usuario.setTipo(Usuario.TipoUsuario.CLIENTE);

        Usuario usuarioSalvo = clienteRepository.save(usuario);

        return mapToUsuarioDTO(usuarioSalvo);
    }

    // DELETAR USUÁRIO PELO CPF
    public void removeUser(String cpf) {
        Usuario usuario = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new CampoInvalidoexception("Usuário não encontrado!"));

        clienteRepository.deleteById(
                Objects.requireNonNull(usuario.getId(), "ID do usuário é obrigatório"));
    }

    // LISTAR TODOS OS USUÁRIOS
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listUsers() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToUsuarioDTO)
                .collect(Collectors.toList());
    }

    // VALIDAR ACESSO ADMIN
    @Transactional(readOnly = true)
    public void validarAcessoAdministrador(String cpf) {
        Usuario usuario = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new CampoInvalidoexception("Usuário não encontrado!"));

        if (usuario.getTipo() != Usuario.TipoUsuario.ADMIN) {
            throw new CampoInvalidoexception("Acesso restrito a administradores.");
        }
    }

    // LISTAR SOMENTE CLIENTES
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listClientes() {
        return clienteRepository.findAll()
                .stream()
                .filter(u -> u.getTipo() == Usuario.TipoUsuario.CLIENTE)
                .map(this::mapToUsuarioDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR USUÁRIO PELO EMAIL
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new CampoInvalidoexception("Usuário não encontrado!"));

        return mapToUsuarioDTO(usuario);
    }

    // BUSCAR USUÁRIO POR ID
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Integer id) {
        if (id == null) {
            throw new CampoInvalidoexception("ID do usuário é obrigatório!");
        }
        Usuario usuario = clienteRepository.findById(id)
                .orElseThrow(() -> new CampoInvalidoexception("Nenhum usuário foi encontrado!"));

        return mapToUsuarioDTO(usuario);
    }

    // LISTAR PAGAMENTOS / GUIAS
    @Transactional(readOnly = true)
    public List<GuiaInssDTO> listarPagamentos() {
        return guiasInssRepository.findAll()
                .stream()
                .map(this::mapToGuiaInssDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR PAGAMENTO PELO ID
    @Transactional(readOnly = true)
    public GuiaInssDTO buscarPagamentoPorId(Integer id) {
        if (id == null) {
            throw new CampoInvalidoexception("ID do pagamento é obrigatório!");
        }

        GuiasInss guia = guiasInssRepository.findById(id)
                .orElseThrow(() -> new CampoInvalidoexception("Pagamento não encontrado!"));

        return mapToGuiaInssDTO(guia);
    }

    // PEGAR GUIAS DO USUÁRIO
    @Transactional(readOnly = true)
    public List<GuiaInssDTO> findByGuia(Integer usuarioId) {
        if (usuarioId == null) {
            throw new CampoInvalidoexception("ID do usuário é obrigatório!");
        }

        return guiasInssRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapToGuiaInssDTO)
                .collect(Collectors.toList());
    }

    // CRIAR PROCESSO
    public ProcessoDTO registrarProcesso(Processo processo, Integer usuarioId) {
        if (usuarioId == null) {
            throw new CampoInvalidoexception("ID do usuário é obrigatório!");
        }

        Usuario usuario = clienteRepository.findById(usuarioId)
                .orElseThrow(() -> new CampoInvalidoexception("Nenhum usuário foi encontrado!"));

        processo.setUsuario(usuario);
        processo.setBiometriaRealizada(false);
        processo.setDataCriacao(LocalDateTime.now());

        Processo processoSalvo = processoRepository.save(processo);
        return mapToProcessoDTO(processoSalvo);
    }

    // ATUALIZA STATUS DO PROCESSO
    public ProcessoDTO atualizaProcessoStatus(Integer processoId, StatusProcesso status) {
        if (processoId == null || status == null) {
            throw new CampoInvalidoexception("ID do processo e novo status são obrigatórios!");
        }

        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new CampoInvalidoexception("Processo não encontrado!"));

        processo.setStatus(status);
        Processo processoAtualizado = processoRepository.save(processo);

        return mapToProcessoDTO(processoAtualizado);
    }

    // CONCLUIR PROCESSO
    public ProcessoDTO atualizaProcessoFinish(Integer processoId) {
        if (processoId == null) {
            throw new CampoInvalidoexception("ID do processo é obrigatório!");
        }

        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new CampoInvalidoexception("Nenhum processo foi encontrado!"));

        processo.setDataConclusao(LocalDateTime.now());
        Processo processoAtualizado = processoRepository.save(processo);

        return mapToProcessoDTO(processoAtualizado);
    }

    // MAPPERS PRIVADOS
    private UsuarioDTO mapToUsuarioDTO(Usuario u) {
        if (u == null) {
            return null;
        }

        return new UsuarioDTO(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getEndereco(),
                u.getCpf(),
                u.getNumeroTelefone(),
                u.getTipo(),
                u.getUltimoAcesso() // ✅ Mapeamento do último acesso incluído corretamente!
        );
    }

    private ProcessoDTO mapToProcessoDTO(Processo p) {
        return new ProcessoDTO(
                p.getId(),
                p.getStatus(),
                p.getBiometriaRealizada(),
                p.getDataCriacao(),
                p.getDataConclusao(),
                p.getUsuario() != null ? p.getUsuario().getId() : null,
                p.getUsuario() != null ? p.getUsuario().getNome() : null);
    }

    // MAPPER DA GUIA INSS
    private GuiaInssDTO mapToGuiaInssDTO(GuiasInss g) {
        if (g == null) {
            return null;
        }

        BigDecimal valorBigDecimal = (g.getValor() != null)
                ? BigDecimal.valueOf(g.getValor())
                : null;

        return new GuiaInssDTO(
                g.getId(),
                g.getCompetencia(),
                g.getVencimento(),
                valorBigDecimal,
                g.isPago(),
                g.getUsuario() != null ? g.getUsuario().getId() : null,
                g.getUsuario() != null ? g.getUsuario().getNome() : null
        );
    }
}