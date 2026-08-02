package com.example.demo.controller;

import com.example.demo.dto.DocumentoDTO;
import com.example.demo.dto.GuiaInssDTO;
import com.example.demo.dto.ProcessoDTO;
import com.example.demo.dto.ProcessoHistoricoDTO;
import com.example.demo.dto.ProcessoMensagemDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Processo;
import com.example.demo.service.AdminService;
import com.example.demo.service.DocumentoService;
import com.example.demo.service.ProcessoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final ProcessoService processoService;

    public AdminController(
            AdminService adminService,
            DocumentoService documentoService,
            ProcessoService processoService) {
        this.adminService = adminService;
        this.documentoService = documentoService;
        this.processoService = processoService;
    }

    // ============================
    // CADASTRAR CLIENTE
    // ============================
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> registerUser(@RequestBody UsuarioDTO usuarioDTO) {
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
    public ResponseEntity<Void> removeUser(@PathVariable String cpf) {
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
    // GERENCIAMENTO DE PROCESSOS
    // ============================

    @PostMapping("/processos/{usuarioId}")
    public ResponseEntity<ProcessoDTO> criarProcesso(
            @PathVariable Integer usuarioId,
            @RequestBody Processo processo) {

        ProcessoDTO novoProcesso = adminService.registrarProcesso(processo, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
    }

    @GetMapping("/processos")
    public ResponseEntity<List<ProcessoDTO>> listarTodosProcessos() {
        return ResponseEntity.ok(processoService.listarProcessos());
    }

    @GetMapping("/processos/{id}")
    public ResponseEntity<ProcessoDTO> buscarProcessoPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(processoService.buscarPorId(id));
    }

    @GetMapping("/clientes/{usuarioId}/processos")
    public ResponseEntity<List<ProcessoDTO>> buscarProcessosPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(processoService.buscarProcessosPorUsuario(usuarioId));
    }

    @PutMapping("/processos/{id}")
    public ResponseEntity<ProcessoDTO> atualizarProcesso(
            @PathVariable Integer id,
            @RequestBody ProcessoDTO processoDTO) {

        ProcessoDTO processoAtualizado = processoService.atualizarProcesso(id, processoDTO);
        return ResponseEntity.ok(processoAtualizado);
    }

    @PostMapping("/processos/{processoId}/historico")
    public ResponseEntity<ProcessoHistoricoDTO> adicionarHistorico(
            @PathVariable Integer processoId,
            @RequestBody ProcessoHistoricoDTO historicoDTO,
            @RequestParam Integer administradorId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(processoService.adicionarHistorico(processoId, historicoDTO, administradorId));
    }

    @GetMapping("/processos/{processoId}/historico")
    public ResponseEntity<List<ProcessoHistoricoDTO>> listarHistorico(@PathVariable Integer processoId) {
        return ResponseEntity.ok(processoService.listarHistorico(processoId));
    }

    @PostMapping("/processos/{processoId}/mensagens")
    public ResponseEntity<ProcessoMensagemDTO> adicionarMensagem(
            @PathVariable Integer processoId,
            @RequestBody ProcessoMensagemDTO mensagemDTO,
            @RequestParam Integer administradorId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(processoService.adicionarMensagem(processoId, mensagemDTO, administradorId));
    }

    @GetMapping("/processos/{processoId}/mensagens")
    public ResponseEntity<List<ProcessoMensagemDTO>> listarMensagens(@PathVariable Integer processoId) {
        return ResponseEntity.ok(processoService.listarMensagens(processoId));
    }

    @PatchMapping("/processos/{processoId}/status")
    public ResponseEntity<ProcessoDTO> atualizarStatusProcesso(
            @PathVariable Integer processoId,
            @RequestParam Processo.StatusProcesso status,
            @RequestParam Integer administradorId) {
        return ResponseEntity.ok(processoService.atualizarStatusProcesso(processoId, status, administradorId));
    }

    @PostMapping("/processos/{processoId}/mensagens/lidas")
    public ResponseEntity<Void> marcarMensagensComoLidas(@PathVariable Integer processoId) {
        processoService.marcarMensagensComoLidas(processoId);
        return ResponseEntity.noContent().build();
    }

    // ============================
    // GERENCIAMENTO DE DOCUMENTOS
    // ============================

    @PostMapping(
            value = "/processos/{processoId}/documentos",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<DocumentoDTO> adicionarDocumento(
            @PathVariable Integer processoId,
            @RequestParam("arquivo") MultipartFile arquivo
    ) throws IOException {

        DocumentoDTO documento = documentoService.salvarDocumento(arquivo, processoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(documento);
    }

    @GetMapping("/documentos")
    public ResponseEntity<List<DocumentoDTO>> listarDocumentos() {
        return ResponseEntity.ok(documentoService.listarDocumentos());
    }

    @GetMapping("/documentos/{id}")
    public ResponseEntity<DocumentoDTO> buscarDocumentoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.buscarPorId(id));
    }

    @PutMapping(
            value = "/documentos/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<DocumentoDTO> atualizarDocumento(
            @PathVariable Long id,
            @RequestParam(value = "arquivo", required = false) MultipartFile arquivo,
            @RequestParam(value = "processoId", required = false) Integer processoId
    ) throws IOException {

        DocumentoDTO documentoAtualizado = documentoService.atualizarDocumento(id, arquivo, processoId);
        return ResponseEntity.ok(documentoAtualizado);
    }

    @DeleteMapping("/documentos/{id}")
    public ResponseEntity<Void> excluirDocumento(@PathVariable Long id) throws IOException {
        documentoService.excluirDocumento(id);
        return ResponseEntity.noContent().build();
    }

    // ============================
    // GUIAS E PAGAMENTOS
    // ============================

    @GetMapping("/clientes/{usuarioId}/guias")
    public ResponseEntity<List<GuiaInssDTO>> listarGuiasCliente(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(adminService.findByGuia(usuarioId));
    }

    @GetMapping("/pagamentos")
    public ResponseEntity<List<GuiaInssDTO>> listarPagamentos() {
        return ResponseEntity.ok(adminService.listarPagamentos());
    }
}