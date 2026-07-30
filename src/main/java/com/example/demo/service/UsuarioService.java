package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.GuiaInssDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.GuiasInss;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.CampoInvalidoexception;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.GuiasInssRepository;

@Service
@Transactional
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
     * Criar nova guia INSS para cliente
     */
    public GuiaInssDTO criarGuia(
            Integer usuarioId,
            GuiasInss guiaInss) {

        if (usuarioId == null) {
            throw new CampoInvalidoexception("ID do usuário é obrigatório!");
        }

        Usuario usuario = clienteRepository.findById(usuarioId)
                .orElseThrow(() -> new CampoInvalidoexception("Usuário não encontrado!"));

        if (guiaInss.getCompetencia() == null || guiaInss.getCompetencia().isBlank()) {
            throw new CampoInvalidoexception("Competência da guia é obrigatória!");
        }

        if (guiaInss.getValor() == null || guiaInss.getValor() <= 0) {
            throw new CampoInvalidoexception("Valor da guia inválido!");
        }

        guiaInss.setUsuario(usuario);
        guiaInss.setPago(false);
        guiaInss.setDataPagamento(null);
        guiaInss.setMensagemPagamento(null);

        GuiasInss guiaSalva = guiasInssRepository.save(guiaInss);
        return mapToGuiaInssDTO(guiaSalva);
    }

    /**
     * Confirmar pagamento da guia
     */
    public GuiaInssDTO confirmarPagamento(Integer guiaId) {
        if (guiaId == null) {
            throw new CampoInvalidoexception("ID da guia é obrigatório!");
        }

        GuiasInss guia = guiasInssRepository.findById(guiaId)
                .orElseThrow(() -> new CampoInvalidoexception("Guia não encontrada!"));

        if (Boolean.TRUE.equals(guia.isPago())) {
            throw new CampoInvalidoexception("Esta guia já foi paga!");
        }

        guia.setPago(true);
        guia.setDataPagamento(LocalDateTime.now());
        guia.setMensagemPagamento("Pagamento confirmado");

        GuiasInss guiaSalva = guiasInssRepository.save(guia);
        return mapToGuiaInssDTO(guiaSalva);
    }

    /**
     * Buscar guias de um cliente
     */
    @Transactional(readOnly = true)
    public List<GuiaInssDTO> buscarGuiasPorUsuario(Integer usuarioId) {
        if (usuarioId == null) {
            throw new CampoInvalidoexception("ID do usuário é obrigatório!");
        }

        List<GuiasInss> guias = guiasInssRepository.findByUsuarioId(usuarioId);

        if (guias.isEmpty()) {
            throw new CampoInvalidoexception("Nenhuma guia encontrada para este usuário!");
        }

        return guias.stream()
                .map(this::mapToGuiaInssDTO)
                .toList();
    }

    /**
     * Buscar cliente pelo ID
     */
    @Transactional(readOnly = true)
    public UsuarioDTO buscarUsuario(Integer id) {
        if (id == null) {
            throw new CampoInvalidoexception("ID do usuário é obrigatório!");
        }

        Usuario usuario = clienteRepository.findById(id)
                .orElseThrow(() -> new CampoInvalidoexception("Usuário não encontrado!"));

        return mapToUsuarioDTO(usuario);
    }

    // MAPPERS PRIVADOS
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
                u.getTipo()
        );
    }
}