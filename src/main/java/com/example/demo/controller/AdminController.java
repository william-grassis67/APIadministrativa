package com.example.demo.controller;

import com.example.demo.dto.DocumentoDTO;
import com.example.demo.dto.GuiaInssDTO;
import com.example.demo.dto.ProcessoDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Processo;
import com.example.demo.service.AdminService;
import com.example.demo.service.DocumentoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final DocumentoService documentoService;

    public AdminController(
            AdminService adminService,
            DocumentoService documentoService) {
        this.adminService = adminService;
        this.documentoService = documentoService;
    }

    // ============================
    // CADASTRAR CLIENTE
    // ============================
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> registerUser(
            @RequestBody UsuarioDTO usuarioDTO) {

        UsuarioDTO usuarioCriado = adminService.registerUser(
                usuarioDTO.getNome(),
                usuarioDTO.getEmail(),
                usuarioDTO.getEndereco(),
                usuarioDTO.getCpf(),
                usuarioDTO.getSenha(),
                usuarioDTO.getNumeroTelefone()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    // ============================
    // REMOVER CLIENTE
    // ============================
    @DeleteMapping("/clientes/{cpf}")
    public ResponseEntity<Void> removeUser(
            @PathVariable String cpf) {

        adminService.removeUser(cpf);
        return ResponseEntity.noContent().build();
    }

    // ============================
    // LISTAR CLIENTES
    // ============================
    @GetMapping("/clientes")
    public ResponseEntity<List<UsuarioDTO>> listClientes() {
        return ResponseEntity.ok(adminService.listClientes());
    }

    // ============================
    // CRIAR PROCESSO PARA CLIENTE
    // ============================
    @PostMapping("/processos/{usuarioId}")
    public ResponseEntity<ProcessoDTO> criarProcesso(
            @PathVariable Integer usuarioId,
            @RequestBody Processo processo) {

        ProcessoDTO novoProcesso = adminService.registrarProcesso(
                processo,
                usuarioId
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
    }

    // ============================
    // ADICIONAR DOCUMENTO AO PROCESSO
    // ============================
    @PostMapping(
            value = "/processos/{processoId}/documentos",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<DocumentoDTO> adicionarDocumento(
            @PathVariable Integer processoId,
            @RequestParam("arquivo") MultipartFile arquivo
    ) throws IOException {

        DocumentoDTO documento = documentoService.salvarDocumento(
                arquivo,
                processoId
        );

        return ResponseEntity.ok(documento);
    }

    // ============================
    // LISTAR GUIAS DE UM CLIENTE
    // ============================
    @GetMapping("/clientes/{usuarioId}/guias")
    public ResponseEntity<List<GuiaInssDTO>> listarGuiasCliente(
            @PathVariable Integer usuarioId) {

        return ResponseEntity.ok(adminService.findByGuia(usuarioId));
    }

    // ============================
    // LISTAR TODOS OS PAGAMENTOS
    // ============================
    @GetMapping("/pagamentos")
    public ResponseEntity<List<GuiaInssDTO>> listarPagamentos() {

        return ResponseEntity.ok(adminService.listarPagamentos());
    }
}