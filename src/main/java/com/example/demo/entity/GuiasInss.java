package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "guias_inss")
public class GuiasInss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false)
    private String competencia;


    private LocalDate vencimento;


    private Double valor;


    @Column(nullable = false)
    private boolean pago = false;


    private LocalDateTime datapagamento;


    @Column(length = 255)
    private String mensagemPagamento;


    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;



    // Obrigatório para o JPA
    public GuiasInss() {

    }



    public GuiasInss(
            Integer id,
            String competencia,
            LocalDate vencimento,
            Double valor,
            boolean pago,
            LocalDateTime datapagamento,
            String mensagemPagamento
    ) {
        this.id = id;
        this.competencia = competencia;
        this.vencimento = vencimento;
        this.valor = valor;
        this.pago = pago;
        this.datapagamento = datapagamento;
        this.mensagemPagamento = mensagemPagamento;
    }



    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }



    public String getCompetencia() {
        return competencia;
    }


    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }



    public LocalDate getVencimento() {
        return vencimento;
    }


    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }



    public Double getValor() {
        return valor;
    }


    public void setValor(Double valor) {
        this.valor = valor;
    }



    public boolean isPaga() {
        return pago;
    }


    public void setPaga(boolean pago) {
        this.pago = pago;
    }



    public LocalDateTime getDataPagamento() {
        return datapagamento;
    }


    public void setDataPagamento(LocalDateTime datapagamento) {
        this.datapagamento = datapagamento;
    }



    public String getMensagemPagamento() {
        return mensagemPagamento;
    }


    public void setMensagemPagamento(String mensagemPagamento) {
        this.mensagemPagamento = mensagemPagamento;
    }



    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}