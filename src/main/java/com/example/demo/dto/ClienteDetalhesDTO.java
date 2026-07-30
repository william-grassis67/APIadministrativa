package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class ClienteDetalhesDTO {

    private UsuarioDTO usuario;
    private List<ProcessoDTO> processos = new ArrayList<>();
    private List<GuiaInssDTO> guias = new ArrayList<>();

    public ClienteDetalhesDTO() {
    }

    public ClienteDetalhesDTO(
            UsuarioDTO usuario,
            List<ProcessoDTO> processos,
            List<GuiaInssDTO> guias
    ) {
        this.usuario = usuario;
        this.processos = processos;
        this.guias = guias;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public List<ProcessoDTO> getProcessos() {
        return processos;
    }

    public void setProcessos(List<ProcessoDTO> processos) {
        this.processos = processos;
    }

    public List<GuiaInssDTO> getGuias() {
        return guias;
    }

    public void setGuias(List<GuiaInssDTO> guias) {
        this.guias = guias;
    }
}