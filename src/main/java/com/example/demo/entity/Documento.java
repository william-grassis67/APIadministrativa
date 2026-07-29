package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Nome original do arquivo enviado
    private String nome;


    // Tipo do arquivo (image/png, application/pdf...)
    private String tipo;


    // Tamanho do arquivo em bytes
    private Long tamanho;


    // Arquivo salvo diretamente no banco
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @JsonIgnore
    private byte[] dados;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processo_id", nullable = false)
    @JsonIgnore
    private Processo processo;


    public Documento() {
    }


    public Documento(
            Long id,
            String nome,
            String tipo,
            Long tamanho,
            byte[] dados,
            Processo processo
    ) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.dados = dados;
        this.processo = processo;
    }


    public Long getId() {
        return id;
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


    public byte[] getDados() {
        return dados;
    }


    public void setDados(byte[] dados) {
        this.dados = dados;
    }


    public Processo getProcesso() {
        return processo;
    }


    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
}