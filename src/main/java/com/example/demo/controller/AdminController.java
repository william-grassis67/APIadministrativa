package com.example.demo.controller;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.GuiasInss;
import com.example.demo.entity.Processo;
import com.example.demo.entity.Usuario;
import com.example.demo.service.AdminService;
import com.example.demo.service.ProcessoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminController {

    private final AdminService adminService;
    private final ProcessoService processoService;

    public AdminController(AdminService adminService, ProcessoService processoService) {
        this.adminService = adminService;
        this.processoService = processoService;
    }

    // CADASTRAR CLIENTE
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> registerUser(
            @RequestBody UsuarioDTO usuarioDTO) {

        UsuarioDTO usuarioCriado = adminService.registerUser(
                usuarioDTO.getNome(),
                usuarioDTO.getEmail(),
                usuarioDTO.getEndereco(),
                usuarioDTO.getCpf(),
                usuarioDTO.getSenha(),
                usuarioDTO.getNumeroTelefone());

        return ResponseEntity.ok(usuarioCriado);
    }

    // REMOVER CLIENTE
    @DeleteMapping("/remove/{cpf}")
    public ResponseEntity<Void> removeUser(@PathVariable String cpf) {

        adminService.removeUser(cpf);

        return ResponseEntity.noContent().build();
    }

    // LISTAR TODOS OS USUÁRIOS
    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> listUsers(
            @RequestParam(required = false) String cpf) {

        if (cpf != null && !cpf.isBlank()) {
            adminService.validarAcessoAdministrador(cpf);
        }

        return ResponseEntity.ok(adminService.listUsers());
    }

    // LISTAR CLIENTES
    @GetMapping("/clientes")
    public ResponseEntity<List<Usuario>> listClientes(
            @RequestParam(required = false) String cpf) {

        if (cpf != null && !cpf.isBlank()) {
            adminService.validarAcessoAdministrador(cpf);
        }

        return ResponseEntity.ok(adminService.listClientes());
    }

    // LISTAR GUIAS DE UM USUÁRIO ESPECÍFICO
    @GetMapping("/payments/guias/{usuarioId}")
    public ResponseEntity<List<GuiasInss>> listarGuiasUsuario(
            @PathVariable Integer usuarioId) {

        return ResponseEntity.ok(
                adminService.findByGuia(usuarioId));
    }

    // LISTAR TODAS AS GUIAS DO SISTEMA
    @GetMapping("/payments/guias")
    public ResponseEntity<List<GuiasInss>> listarTodasGuias() {

        return ResponseEntity.ok(
                adminService.listarPagamentos());
    }

    // CRIAR Processo
    @PostMapping("/processos/{id}")
    public Processo registrarProcesso(
            @PathVariable Integer id,
            @RequestBody Processo processo) {

        return adminService.registrarProcesso(processo, id);
    }

    @GetMapping("/cliente/processo/{id}")
    public List<Processo> buscarProcesso(@PathVariable Integer id) {
        return processoService.buscarProcessosPorUsuario(id);
    }
}