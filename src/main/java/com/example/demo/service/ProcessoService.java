package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Processo;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ProcessoRepository;

@Service
public class ProcessoService {

    private final ProcessoRepository processoRepository;
    private final ClienteRepository clienteRepository;

    public ProcessoService(ProcessoRepository processoRepository,
                           ClienteRepository clienteRepository) {
        this.processoRepository = processoRepository;
        this.clienteRepository = clienteRepository;
    }

    // LISTAR TODOS OS PROCESSOS
    public List<Processo> listarProcessos() {
        return processoRepository.findAll();
    }

    // BUSCAR TODOS OS PROCESSOS DE UM USUÁRIO
    public List<Processo> buscarProcessosPorUsuario(Integer usuarioId) {

        // Verifica se o usuário existe
        Usuario usuario = clienteRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // Busca os processos do usuário
        List<Processo> processos = processoRepository.findAllByUsuarioId(usuario.getId());

        if (processos.isEmpty()) {
            throw new RuntimeException("Nenhum processo encontrado para este usuário!");
        }

        return processos;
    }

}