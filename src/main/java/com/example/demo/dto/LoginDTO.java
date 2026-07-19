package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.Usuario;

public class LoginDTO {

    private String cpf;
    private String senha;

    private String nome;
    private String email;
    private Usuario.TipoUsuario tipo;
    private LocalDateTime ultimoAcesso;
    private boolean pagamentoPago;
    private String statusPagamento;
    private LocalDateTime dataPagamento;
    private String mensagemPagamento;
    private boolean acessoLiberado;
    private String token;

    public LoginDTO() {
    }

    // Construtor usado na requisição de login
    public LoginDTO(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    // Construtor usado na resposta do login
    public LoginDTO(
            String cpf,
            String nome,
            String email,
            Usuario.TipoUsuario tipo,
            LocalDateTime ultimoAcesso,
            boolean pagamentoPago,
            String statusPagamento,
            LocalDateTime dataPagamento,
            String mensagemPagamento,
            boolean acessoLiberado,
            String token
    ) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        this.ultimoAcesso = ultimoAcesso;
        this.pagamentoPago = pagamentoPago;
        this.statusPagamento = statusPagamento;
        this.dataPagamento = dataPagamento;
        this.mensagemPagamento = mensagemPagamento;
        this.acessoLiberado = acessoLiberado;
        this.token = token;
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

    public Usuario.TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(Usuario.TipoUsuario tipo) {
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

    public boolean isAcessoLiberado() {
        return acessoLiberado;
    }

    public void setAcessoLiberado(boolean acessoLiberado) {
        this.acessoLiberado = acessoLiberado;
    }

    public String getToken(){
        return this.token;
    }

    public void setToken(String token){
        this.token = token;
    }
}