package com.example.demo.dto;

public class DocumentoDTO {

    private Long id;

    private String nome;

    private String tipo;

    private Long tamanho;

    private String caminho;


    public DocumentoDTO() {
    }


    public DocumentoDTO(
            Long id,
            String nome,
            String tipo,
            Long tamanho,
            String caminho
    ) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.caminho = caminho;
    }


    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }


    public String getTipo() {
        return tipo;
    }


    public Long getTamanho() {
        return tamanho;
    }


    public String getCaminho() {
        return caminho;
    }
}