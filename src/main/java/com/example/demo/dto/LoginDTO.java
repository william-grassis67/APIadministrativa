package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.GuiasInss;
import com.example.demo.entity.Usuario;

public class LoginDTO {

    // Dados de entrada
    private String cpf;
    private String senha;


    // Dados do usuário
    private String nome;
    private String email;
    private Usuario.TipoUsuario tipo;
    private LocalDateTime ultimoAcesso;


    // Guias do INSS
    private List<GuiasInss> guiasInss;


    // Informações de pagamento
    private String mensagemPagamento;
    private boolean pagamentoPago;
    private String statusPagamento;
    private LocalDateTime dataPagamento;


    // Outros dados
    private Integer numeroTelefone;

    private boolean acessoLiberado;

    private String token;



    public LoginDTO() {
    }



    // Login recebido pelo frontend
    public LoginDTO(
            String cpf,
            String senha
    ) {
        this.cpf = cpf;
        this.senha = senha;
    }




    // Resposta do login
    public LoginDTO(
            String cpf,
            String nome,
            String email,
            Usuario.TipoUsuario tipo,
            LocalDateTime ultimoAcesso,
            List<GuiasInss> guiasInss,
            String mensagemPagamento,
            Integer numeroTelefone,
            boolean pagamentoPago,
            String statusPagamento,
            LocalDateTime dataPagamento,
            boolean acessoLiberado,
            String token
    ) {

        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        this.ultimoAcesso = ultimoAcesso;

        this.guiasInss = guiasInss;

        this.mensagemPagamento = mensagemPagamento;
        this.numeroTelefone = numeroTelefone;

        this.pagamentoPago = pagamentoPago;
        this.statusPagamento = statusPagamento;
        this.dataPagamento = dataPagamento;

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



    public List<GuiasInss> getGuiasInss() {
        return guiasInss;
    }

    public void setGuiasInss(List<GuiasInss> guiasInss) {
        this.guiasInss = guiasInss;
    }



    public String getMensagemPagamento() {
        return mensagemPagamento;
    }

    public void setMensagemPagamento(String mensagemPagamento) {
        this.mensagemPagamento = mensagemPagamento;
    }



    public Integer getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(Integer numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
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



    public boolean isAcessoLiberado() {
        return acessoLiberado;
    }

    public void setAcessoLiberado(boolean acessoLiberado) {
        this.acessoLiberado = acessoLiberado;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}