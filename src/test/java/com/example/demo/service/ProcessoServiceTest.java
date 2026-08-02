package com.example.demo.service;

import com.example.demo.dto.ProcessoHistoricoDTO;
import com.example.demo.entity.Processo;
import com.example.demo.entity.ProcessoHistorico;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ProcessoHistoricoRepository;
import com.example.demo.repository.ProcessoMensagemRepository;
import com.example.demo.repository.ProcessoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessoServiceTest {

    @Test
    void deveAdicionarHistoricoEAtualizarUltimaAtualizacao() {
        ProcessoRepository processoRepository = mock(ProcessoRepository.class);
        ClienteRepository clienteRepository = mock(ClienteRepository.class);
        ProcessoHistoricoRepository historicoRepository = mock(ProcessoHistoricoRepository.class);
        ProcessoMensagemRepository mensagemRepository = mock(ProcessoMensagemRepository.class);

        ProcessoService service = new ProcessoService(
                processoRepository,
                clienteRepository,
                historicoRepository,
                mensagemRepository
        );

        Processo processo = new Processo();
        processo.setId(10);
        processo.setStatus(Processo.StatusProcesso.AGUARDANDO_DOCUMENTOS);
        processo.setUltimaAtualizacao(LocalDateTime.of(2024, 1, 1, 10, 0));

        Usuario administrador = new Usuario();
        administrador.setId(3);
        administrador.setNome("Admin Teste");

        when(processoRepository.findById(10)).thenReturn(Optional.of(processo));
        when(processoRepository.save(any(Processo.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(historicoRepository.save(any(ProcessoHistorico.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(clienteRepository.findById(3)).thenReturn(Optional.of(administrador));

        ProcessoHistoricoDTO dto = new ProcessoHistoricoDTO();
        dto.setTitulo("Documentação recebida");
        dto.setDescricao("O cliente enviou os documentos necessários.");
        dto.setDataHora(LocalDateTime.of(2024, 1, 2, 11, 30));

        ProcessoHistoricoDTO resultado = service.adicionarHistorico(10, dto, administrador.getId());

        assertEquals("Documentação recebida", resultado.getTitulo());
        assertEquals(administrador.getId(), resultado.getAdministradorId());
        assertEquals(1, processo.getNotificacoesNaoLidas());
        assertNotNull(processo.getUltimaAtualizacao());
    }
}
