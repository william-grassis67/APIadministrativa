package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "processos")
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    private String tipo;

    @Enumerated(EnumType.STRING)
    private StatusProcesso status;

    public enum StatusProcesso {
        AGUARDANDO_DOCUMENTOS,
        EM_ANALISE,
        AGUARDANDO_PAGAMENTO,
        FINALIZADO
    }

    private String numeroProcesso;

    private Double valorProcesso;

    private String pendencias;

    private String documentosPendentes;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    private Boolean pagamentoRealizado;

    private Boolean biometriaRealizada;

    private LocalDateTime dataCriacao;

    private LocalDateTime ultimaAtualizacao;

    private LocalDateTime dataConclusao;

    public Processo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
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

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Double getValorProcesso() {
        return valorProcesso;
    }

    public void setValorProcesso(Double valorProcesso) {
        this.valorProcesso = valorProcesso;
    }

    public String getPendencias() {
        return pendencias;
    }

    public void setPendencias(String pendencias) {
        this.pendencias = pendencias;
    }

    public String getDocumentosPendentes() {
        return documentosPendentes;
    }

    public void setDocumentosPendentes(String documentosPendentes) {
        this.documentosPendentes = documentosPendentes;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getPagamentoRealizado() {
        return pagamentoRealizado;
    }

    public void setPagamentoRealizado(Boolean pagamentoRealizado) {
        this.pagamentoRealizado = pagamentoRealizado;
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

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}