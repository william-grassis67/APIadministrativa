package com.example.demo.controller;

import com.example.demo.entity.Documento;
import com.example.demo.service.DocumentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos")
@CrossOrigin(origins = "*")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    // Salvar um documento
    @PostMapping("/{processoId}")
    public Documento salvarDocumento(@RequestBody Documento documento,
                                     @PathVariable Integer processoId) {

        return documentoService.salvarDocumento(documento, processoId);
    }

    // Listar todos os documentos
    @GetMapping
    public List<Documento> listarDocumentos() {
        return documentoService.listarDocumentos();
    }

    // Buscar documento por ID
    @GetMapping("/{id}")
    public Documento buscarPorId(@PathVariable Long id) {
        return documentoService.buscarPorId(id);
    }

    // Atualizar documento
    @PutMapping("/{id}")
    public Documento atualizarDocumento(@PathVariable Long id,
                                        @RequestBody Documento documento) {

        return documentoService.atualizarDocumento(id, documento);
    }

    // Excluir documento
    @DeleteMapping("/{id}")
    public void excluirDocumento(@PathVariable Long id) {
        documentoService.excluirDocumento(id);
    }
}