package com.example.demo.dto;

import com.example.demo.entity.Processo.StatusProcesso;
import java.time.LocalDateTime;

public class ProcessoDTO {

    private Integer id;
    private String tipo;
    private StatusProcesso status;
    private Boolean biometriaRealizada;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
    private Integer usuarioId;
    private String nomeUsuario;

    // 1. Construtor padrão (sem argumentos)
    public ProcessoDTO() {
    }

    // 2. Construtor completo com 8 parâmetros (Recomendado)
    public ProcessoDTO(
            Integer id,
            String tipo,
            StatusProcesso status,
            Boolean biometriaRealizada,
            LocalDateTime dataCriacao,
            LocalDateTime dataConclusao,
            Integer usuarioId,
            String nomeUsuario
    ) {
        this.id = id;
        this.tipo = tipo;
        this.status = status;
        this.biometriaRealizada = biometriaRealizada;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
    }

    // 3. Construtor secundário com 7 parâmetros (sem 'tipo') para evitar erros de compilação
    public ProcessoDTO(
            Integer id,
            StatusProcesso status,
            Boolean biometriaRealizada,
            LocalDateTime dataCriacao,
            LocalDateTime dataConclusao,
            Integer usuarioId,
            String nomeUsuario
    ) {
        this.id = id;
        this.status = status;
        this.biometriaRealizada = biometriaRealizada;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public StatusProcesso getStatus() {
        return status;
    }

    public void setStatus(StatusProcesso status) {
        this.status = status;
    }

    public Boolean getBiometriaRealizada() {
        return biometriaRealizada;
    }

    public void setBiometriaRealizada(Boolean biometriaRealizada) {
        this.biometriaRealizada = biometriaRealizada;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
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