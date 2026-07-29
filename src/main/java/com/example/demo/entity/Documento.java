package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Caminho onde o arquivo foi salvo
    @Column(nullable = false)
    private String caminho;


    // Nome original do arquivo enviado
    @Column(nullable = false)
    private String nome;


    // Tipo do arquivo (application/pdf, image/png...)
    private String tipo;


    // Tamanho do arquivo em bytes
    private Long tamanho;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processo_id", nullable = false)
    @JsonIgnore
    private Processo processo;


    public Documento() {
    }


    public Documento(
            Long id,
            String caminho,
            String nome,
            String tipo,
            Long tamanho,
            Processo processo
    ) {
        this.id = id;
        this.caminho = caminho;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.processo = processo;
    }


    public Long getId() {
        return id;
    }


    public String getCaminho() {
        return caminho;
    }


    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public Long getTamanho() {
        return tamanho;
    }


    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }


    public Processo getProcesso() {
        return processo;
    }


    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
}