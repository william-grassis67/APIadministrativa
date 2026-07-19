package com.example.demo.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean pago;

    private LocalDateTime dataPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;


    public Pagamento() {
    }


    public Pagamento(Integer id, boolean pago, Usuario usuario, LocalDateTime dataPagamento) {
        this.id = id;
        this.pago = pago;
        this.usuario = usuario;
        this.dataPagamento = dataPagamento;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public boolean isPago() {
        return pago;
    }


    public void setPago(boolean pago) {
        this.pago = pago;
    }


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }


    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}