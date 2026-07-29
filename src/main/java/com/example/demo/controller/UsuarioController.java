package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.GuiasInss;
import com.example.demo.entity.Processo;
import com.example.demo.service.ProcessoService;
import com.example.demo.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ProcessoService processoService;
    public UsuarioController(UsuarioService usuarioService, ProcessoService processoService) {
        this.usuarioService = usuarioService;
        this.processoService = processoService;
    }

    // Criar uma nova guia INSS
    @PostMapping("/guias/{usuarioId}")
    public ResponseEntity<GuiasInss> criarGuia(
            @PathVariable Integer usuarioId,
            @RequestBody GuiasInss guiaInss) {

        GuiasInss novaGuia = usuarioService.criarGuia(
                usuarioId,
                guiaInss
        );

        return ResponseEntity.ok(novaGuia);
    }

    // Confirmar pagamento da guia
    @PutMapping("/pagamento/{guiaId}")
    public ResponseEntity<GuiasInss> confirmarPagamento(
            @PathVariable Integer guiaId) {

        GuiasInss guiaAtualizada =
                usuarioService.confirmarPagamento(guiaId);

        return ResponseEntity.ok(guiaAtualizada);
    }

    @GetMapping("/me/processos/{id}")
    public List<Processo> buscarProcesso(@PathVariable Integer userId){
        return processoService.buscarProcessosPorUsuario(userId);
    }
}