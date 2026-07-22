package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.GuiasInss;
import com.example.demo.service.UsuarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/pagamento/{usuarioId}")
    public ResponseEntity<GuiasInss> registrarPagamento(
            @PathVariable Integer usuarioId,
            @RequestBody GuiasInss guiaInss) {

        GuiasInss novoPagamento = usuarioService.paymentRegister(usuarioId, guiaInss);

        return ResponseEntity.ok(novoPagamento);
    }
}
