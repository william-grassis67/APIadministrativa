package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GuiaInssDTO {

    private Integer id;
    private String competencia;
    private LocalDate vencimento;
    private BigDecimal valor;
    private Boolean pago;
    private Integer usuarioId;
    private String nomeUsuario;

    // 1. Construtor padrão (sem argumentos)
    public GuiaInssDTO() {
    }

    // 2. Construtor completo (7 parâmetros) - Recomendado para o AdminService
    public GuiaInssDTO(
            Integer id,
            String competencia,
            LocalDate vencimento,
            BigDecimal valor,
            Boolean pago,
            Integer usuarioId,
            String nomeUsuario
    ) {
        this.id = id;
        this.competencia = competencia;
        this.vencimento = vencimento;
        this.valor = valor;
        this.pago = pago;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
    }

    // 3. Construtor base (5 parâmetros) - Sem vínculo direto do usuário
    public GuiaInssDTO(
            Integer id,
            String competencia,
            LocalDate vencimento,
            BigDecimal valor,
            Boolean pago
    ) {
        this.id = id;
        this.competencia = competencia;
        this.vencimento = vencimento;
        this.valor = valor;
        this.pago = pago;
    }

    // 4. Construtor com suporte a Double para valor (Compatibilidade legada)
    public GuiaInssDTO(
            Integer id,
            String competencia,
            LocalDate vencimento,
            Double valor,
            Boolean pago
    ) {
        this.id = id;
        this.competencia = competencia;
        this.vencimento = vencimento;
        this.valor = valor != null ? BigDecimal.valueOf(valor) : null;
        this.pago = pago;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    // Setter utilitário para aceitar Double diretamente
    public void setValor(Double valor) {
        this.valor = valor != null ? BigDecimal.valueOf(valor) : null;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}