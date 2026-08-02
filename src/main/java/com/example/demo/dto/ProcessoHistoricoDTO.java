package com.example.demo.dto;

import java.time.LocalDateTime;

public class ProcessoHistoricoDTO {

    private Integer id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private Integer administradorId;
    private String nomeAdministrador;

    public ProcessoHistoricoDTO() {
    }

    public ProcessoHistoricoDTO(Integer id, String titulo, String descricao, LocalDateTime dataHora,
                               Integer administradorId, String nomeAdministrador) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.administradorId = administradorId;
        this.nomeAdministrador = nomeAdministrador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getAdministradorId() {
        return administradorId;
    }

    public void setAdministradorId(Integer administradorId) {
        this.administradorId = administradorId;
    }

    public String getNomeAdministrador() {
        return nomeAdministrador;
    }

    public void setNomeAdministrador(String nomeAdministrador) {
        this.nomeAdministrador = nomeAdministrador;
    }
}
