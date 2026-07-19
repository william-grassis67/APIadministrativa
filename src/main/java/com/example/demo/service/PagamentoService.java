package com.example.demo.service;

import com.example.demo.entity.Pagamento;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Pagamento registrarPagamento(Pagamento pagamentoRecebido) {
        Pagamento pagamento = pagamentoRepository
                .findTopByUsuarioOrderByIdDesc(pagamentoRecebido.getUsuario())
                .orElse(new Pagamento());

        pagamento.setPago(pagamentoRecebido.isPago());
        pagamento.setUsuario(pagamentoRecebido.getUsuario());
        pagamento.setDataPagamento(pagamentoRecebido.isPago() ? LocalDateTime.now() : null);

        return pagamentoRepository.save(pagamento);
    }

    public Pagamento buscarUltimoPagamento(Usuario usuario) {
        return pagamentoRepository.findTopByUsuarioOrderByIdDesc(usuario).orElse(null);
    }

    public boolean isPagamentoPago(Usuario usuario) {
        Pagamento pagamento = buscarUltimoPagamento(usuario);
        return pagamento != null && pagamento.isPago();
    }

    public String getStatusPagamento(Usuario usuario) {
        return isPagamentoPago(usuario) ? "PAGO" : "PENDENTE";
    }
}