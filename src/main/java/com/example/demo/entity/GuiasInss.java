package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "guias_inss")
public class GuiasInss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String competencia;

    private LocalDate vencimento;

    private Double valor;

    @Column(nullable = false)
    private boolean pago = false;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(length = 255)
    private String mensagemPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    public GuiasInss() {
    }

    public GuiasInss(
            Integer id,
            String competencia,
            LocalDate vencimento,
            Double valor,
            boolean pago,
            LocalDateTime dataPagamento,
            String mensagemPagamento,
            Usuario usuario
    ) {
        this.id = id;
        this.competencia = competencia;
        this.vencimento = vencimento;
        this.valor = valor;
        this.pago = pago;
        this.dataPagamento = dataPagamento;
        this.mensagemPagamento = mensagemPagamento;
        this.usuario = usuario;
    }

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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getMensagemPagamento() {
        return mensagemPagamento;
    }

    public void setMensagemPagamento(String mensagemPagamento) {
        this.mensagemPagamento = mensagemPagamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}