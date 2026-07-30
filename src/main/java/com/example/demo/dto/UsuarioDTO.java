package com.example.demo.dto;

import com.example.demo.entity.Usuario.TipoUsuario;

public class UsuarioDTO {

    private Integer id;
    private String nome;
    private String email;
    private String endereco;
    private String cpf;
    private String numeroTelefone;
    private String senha;
    private TipoUsuario tipo;

    public UsuarioDTO() {
    }

    public UsuarioDTO(
            Integer id,
            String nome,
            String email,
            String endereco,
            String cpf,
            String numeroTelefone,
            TipoUsuario tipo
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.cpf = cpf;
        this.numeroTelefone = numeroTelefone;
        this.tipo = tipo;
    }

    public UsuarioDTO(
            Integer id,
            String nome,
            String email,
            String endereco,
            String cpf,
            String numeroTelefone
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.cpf = cpf;
        this.numeroTelefone = numeroTelefone;
    }

    public UsuarioDTO(
            Integer id,
            String nome,
            String email,
            String endereco,
            String cpf,
            String numeroTelefone,
            String senha,
            TipoUsuario tipo
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.cpf = cpf;
        this.numeroTelefone = numeroTelefone;
        this.senha = senha;
        this.tipo = tipo;
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

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
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
}