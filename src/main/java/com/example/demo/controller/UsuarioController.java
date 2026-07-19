package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Pagamento;
import com.example.demo.service.PagamentoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private final PagamentoService pagamentoService;

    public UsuarioController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/pagamento")
    public ResponseEntity<Pagamento> registrarPagamento(@RequestBody Pagamento pagamento) {
        Pagamento novoPagamento = pagamentoService.registrarPagamento(pagamento);
        return ResponseEntity.ok(novoPagamento);
    }
}
