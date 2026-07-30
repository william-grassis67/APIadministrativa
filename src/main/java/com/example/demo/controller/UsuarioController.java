package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.GuiaInssDTO;
import com.example.demo.dto.ProcessoDTO;
import com.example.demo.service.ProcessoService;
import com.example.demo.service.UsuarioService;

@RestController
@RequestMapping("/api/cliente")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ProcessoService processoService;

    public UsuarioController(
            UsuarioService usuarioService,
            ProcessoService processoService) {
        this.usuarioService = usuarioService;
        this.processoService = processoService;
    }

    // ============================
    // CLIENTE CONFIRMA PAGAMENTO
    // ============================
    @PutMapping("/pagamento/{guiaId}")
    public ResponseEntity<GuiaInssDTO> confirmarPagamento(
            @PathVariable Integer guiaId) {

        GuiaInssDTO guiaAtualizada = usuarioService.confirmarPagamento(guiaId);

        return ResponseEntity.ok(guiaAtualizada);
    }

    // ============================
    // CLIENTE CONSULTA PROCESSOS
    // ============================
    @GetMapping("/processos/{usuarioId}")
    public ResponseEntity<List<ProcessoDTO>> buscarProcessos(
            @PathVariable Integer usuarioId) {

        List<ProcessoDTO> processos = processoService.buscarProcessosPorUsuario(usuarioId);

        return ResponseEntity.ok(processos);
    }

    // ============================
    // CLIENTE CONSULTA SUAS GUIAS
    // ============================
    @GetMapping("/guias/{usuarioId}")
    public ResponseEntity<List<GuiaInssDTO>> buscarGuias(
            @PathVariable Integer usuarioId) {

        List<GuiaInssDTO> guias = usuarioService.buscarGuiasPorUsuario(usuarioId);

        return ResponseEntity.ok(guias);
    }
}