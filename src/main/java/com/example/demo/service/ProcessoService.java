package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ProcessoDTO;
import com.example.demo.entity.Processo;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.CampoInvalidoexception;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ProcessoRepository;

@Service
@Transactional
public class ProcessoService {

    private final ProcessoRepository processoRepository;
    private final ClienteRepository clienteRepository;

    public ProcessoService(
            ProcessoRepository processoRepository,
            ClienteRepository clienteRepository
    ) {
        this.processoRepository = processoRepository;
        this.clienteRepository = clienteRepository;
    }

    // LISTAR TODOS OS PROCESSOS
    @Transactional(readOnly = true)
    public List<ProcessoDTO> listarProcessos() {
        return processoRepository.findAll()
                .stream()
                .map(this::mapToProcessoDTO)
                .toList();
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

    // MAPPER PRIVADO
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
}