package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.GuiasInss;
import com.example.demo.entity.Processo;
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
    public ResponseEntity<GuiasInss> confirmarPagamento(
            @PathVariable Integer guiaId) {


        GuiasInss guiaAtualizada =
                usuarioService.confirmarPagamento(guiaId);


        return ResponseEntity.ok(guiaAtualizada);

    }







    // ============================
    // CLIENTE CONSULTA PROCESSOS
    // ============================

    @GetMapping("/processos/{usuarioId}")
    public ResponseEntity<List<Processo>> buscarProcessos(
            @PathVariable Integer usuarioId) {


        List<Processo> processos =
                processoService.buscarProcessosPorUsuario(usuarioId);


        return ResponseEntity.ok(processos);

    }







    // ============================
    // CLIENTE CONSULTA SUAS GUIAS
    // ============================

    @GetMapping("/guias/{usuarioId}")
    public ResponseEntity<List<GuiasInss>> buscarGuias(
            @PathVariable Integer usuarioId) {


        List<GuiasInss> guias =
                usuarioService.buscarGuiasPorUsuario(usuarioId);


        return ResponseEntity.ok(guias);

    }


}