package com.example.demo.dto;

import java.util.List;

public class ClienteDetalhesDTO {

    private UsuarioDTO usuario;
    private List<ProcessoDTO> processos;
    private List<GuiaInssDTO> guias;


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


    public List<ProcessoDTO> getProcessos() {
        return processos;
    }


    public List<GuiaInssDTO> getGuias() {
        return guias;
    }
}