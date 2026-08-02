package com.example.demo.dto;

import com.example.demo.entity.ProcessoMensagem.StatusMensagem;
import java.time.LocalDateTime;

public class ProcessoMensagemDTO {

    private Integer id;
    private String texto;
    private LocalDateTime dataHora;
    private Integer administradorId;
    private String nomeAdministrador;
    private StatusMensagem status;

    public ProcessoMensagemDTO() {
    }

    public ProcessoMensagemDTO(Integer id, String texto, LocalDateTime dataHora, Integer administradorId,
                               String nomeAdministrador, StatusMensagem status) {
        this.id = id;
        this.texto = texto;
        this.dataHora = dataHora;
        this.administradorId = administradorId;
        this.nomeAdministrador = nomeAdministrador;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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

    public StatusMensagem getStatus() {
        return status;
    }

    public void setStatus(StatusMensagem status) {
        this.status = status;
    }
}
