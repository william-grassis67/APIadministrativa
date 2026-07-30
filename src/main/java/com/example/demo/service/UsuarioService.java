package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.GuiasInss;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.GuiasInssRepository;

@Service
public class UsuarioService {

        private final GuiasInssRepository guiasInssRepository;
        private final ClienteRepository clienteRepository;

        public UsuarioService(
                        GuiasInssRepository guiasInssRepository,
                        ClienteRepository clienteRepository) {

                this.guiasInssRepository = guiasInssRepository;
                this.clienteRepository = clienteRepository;

        }

        /**
         * Criar nova guia INSS
         */
        public GuiasInss criarGuia(
                        Integer usuarioId,
                        GuiasInss guiaInss) {

                Usuario usuario = clienteRepository.findById(usuarioId)

                                .orElseThrow(() -> new RuntimeException(
                                                "Usuário não encontrado"));

                if (guiaInss.getCompetencia() == null ||
                                guiaInss.getCompetencia().isBlank()) {

                        throw new RuntimeException(
                                        "Competência obrigatória");

                }

                if (guiaInss.getValor() == null ||
                                guiaInss.getValor() <= 0) {

                        throw new RuntimeException(
                                        "Valor inválido");

                }

                // Vincula a guia ao usuário
                guiaInss.setUsuario(usuario);

                // Guia começa como pendente
                guiaInss.setPago(false);

                // Sem data de pagamento inicialmente
                guiaInss.setDataPagamento(null);

                return guiasInssRepository.save(guiaInss);

        }

        /**
         * Confirmar pagamento de uma guia
         */
        public GuiasInss confirmarPagamento(
                        Integer guiaId) {

                GuiasInss guia = guiasInssRepository.findById(guiaId)

                                .orElseThrow(() -> new RuntimeException(
                                                "Guia não encontrada"));

                if (guia.isPago()) {

                        throw new RuntimeException(
                                        "Esta guia já foi paga");

                }

                guia.setPago(true);

                guia.setDataPagamento(
                                LocalDateTime.now());

                guia.setMensagemPagamento(
                                "Pagamento confirmado");

                return guiasInssRepository.save(guia);

        }

        public List<GuiasInss> buscarGuiasPorUsuario(Integer id) {
                List<GuiasInss> guiasInss = guiasInssRepository.findByUsuarioId(id);

                if (guiasInss.isEmpty()) {
                        throw new RuntimeException("Nenhuma guia encontrada para este usuário");
                }

                return guiasInss;
        }

}