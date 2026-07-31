package com.example.demo.dto;

import java.time.LocalDateTime;
import com.example.demo.entity.Usuario.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

public class UsuarioDTO {

    private Integer id;
    private String nome;
    private String email;
    private String endereco;
    private String cpf;
    private String numeroTelefone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String senha;

    private TipoUsuario tipo;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ultimoAcesso;

    /**
     * Construtor padrão necessário para desserialização do Jackson.
     */
    public UsuarioDTO() {
    }

    /**
     * Construtor completo contendo todos os atributos da classe.
     */
    public UsuarioDTO(
            Integer id,
            String nome,
            String email,
            String endereco,
            String cpf,
            String numeroTelefone,
            String senha,
            TipoUsuario tipo,
            LocalDateTime ultimoAcesso
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.cpf = cpf;
        this.numeroTelefone = numeroTelefone;
        this.senha = senha;
        this.tipo = tipo;
        this.ultimoAcesso = ultimoAcesso;
    }

    /**
     * Construtor para exibição geral sem a senha, mas com o tipo e último acesso.
     */
    public UsuarioDTO(
            Integer id,
            String nome,
            String email,
            String endereco,
            String cpf,
            String numeroTelefone,
            TipoUsuario tipo,
            LocalDateTime ultimoAcesso
    ) {
        this(id, nome, email, endereco, cpf, numeroTelefone, null, tipo, ultimoAcesso);
    }

    /**
     * Construtor auxiliar mantido para compatibilidade com fluxos antigos sem a senha/último acesso.
     */
    public UsuarioDTO(
            Integer id,
            String nome,
            String email,
            String endereco,
            String cpf,
            String numeroTelefone,
            TipoUsuario tipo
    ) {
        this(id, nome, email, endereco, cpf, numeroTelefone, null, tipo, null);
    }

    /**
     * Construtor auxiliar mantido para compatibilidade com cenários onde a senha é trafegada.
     */
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
        this(id, nome, email, endereco, cpf, numeroTelefone, senha, tipo, null);
    }

    // Getters e Setters

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

    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(LocalDateTime ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }
}