package com.example.demo.service;

import com.example.demo.entity.Documento;
import com.example.demo.entity.Processo;
import com.example.demo.repository.DocumentoRepository;
import com.example.demo.repository.ProcessoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ProcessoRepository processoRepository;

    public DocumentoService(DocumentoRepository documentoRepository,
                            ProcessoRepository processoRepository) {
        this.documentoRepository = documentoRepository;
        this.processoRepository = processoRepository;
    }

    // Salvar um documento
    public Documento salvarDocumento(Documento documento, Integer processoId) {

        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado!"));

        documento.setProcesso(processo);

        return documentoRepository.save(documento);
    }

    // Buscar todos os documentos
    public List<Documento> listarDocumentos() {
        return documentoRepository.findAll();
    }

    // Buscar documento por ID
    public Documento buscarPorId(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado!"));
    }

    // Atualizar documento
    public Documento atualizarDocumento(Long id, Documento novosDados) {

        Documento documento = buscarPorId(id);

        if (novosDados.getNome() != null) {
            documento.setNome(novosDados.getNome());
        }

        if (novosDados.getTipo() != null) {
            documento.setTipo(novosDados.getTipo());
        }

        if (novosDados.getTamanho() != null) {
            documento.setTamanho(novosDados.getTamanho());
        }

        if (novosDados.getCaminho() != null) {
            documento.setCaminho(novosDados.getCaminho());
        }

        return documentoRepository.save(documento);
    }

    // Excluir documento
    public void excluirDocumento(Long id) {

        Documento documento = buscarPorId(id);

        documentoRepository.delete(documento);
    }
}