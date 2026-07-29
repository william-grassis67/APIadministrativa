package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private String senha;

    @Column(name = "ultimoacesso")
    private LocalDateTime ultimoAcesso;

    @Column (name = "numeroTelefone")
    private String numeroTelefone;

    @Transient
    private boolean pagamentoPago;

    @Transient
    private String statusPagamento;

    @Transient
    private LocalDateTime dataPagamento;

    @Transient
    private String mensagemPagamento;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<GuiasInss> pagamentos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Processo> processo = new ArrayList<>();

    public enum TipoUsuario {
        ADMIN,
        CLIENTE
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    public Usuario() {
    }

    public Usuario(
            Integer id,
            String nome,
            String email,
            String endereco,
            String cpf,
            String senha,
            TipoUsuario tipo,
            LocalDateTime ultimoAcesso,
            String numeroTelefone

    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.cpf = cpf;
        this.senha = senha;
        this.tipo = tipo;
        this.ultimoAcesso = ultimoAcesso;
        this.numeroTelefone = numeroTelefone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(LocalDateTime ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public boolean isPagamentoPago() {
        return pagamentoPago;
    }

    public void setPagamentoPago(boolean pagamentoPago) {
        this.pagamentoPago = pagamentoPago;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
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

    public List<GuiasInss> getGuiasInsses() {
        return this.pagamentos;
    }

    public void setGuiasInss(List<GuiasInss> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public List<Processo> getProcesso(){
        return this.processo;
    }

    public void setProcesso(List<Processo> processo){
        this.processo = processo;
    }

    public String getNumeroTelefone(){
        return this.numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone){
        this.numeroTelefone = numeroTelefone;
    }
}