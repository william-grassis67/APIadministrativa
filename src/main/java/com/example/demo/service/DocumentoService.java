package com.example.demo.service;

import com.example.demo.dto.DocumentoDTO;
import com.example.demo.entity.Documento;
import com.example.demo.entity.Processo;
import com.example.demo.exception.CampoInvalidoexception;
import com.example.demo.repository.DocumentoRepository;
import com.example.demo.repository.ProcessoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ProcessoRepository processoRepository;
        private final String pastaUpload = "/tmp/uploads";

    public DocumentoService(
            DocumentoRepository documentoRepository,
            ProcessoRepository processoRepository
    ) {
        this.documentoRepository = documentoRepository;
        this.processoRepository = processoRepository;
    }

    // SALVAR DOCUMENTO
    public DocumentoDTO salvarDocumento(MultipartFile arquivo, Integer processoId) throws IOException {
        validarArquivo(arquivo);

        if (processoId == null) {
            throw new CampoInvalidoexception("ID do processo é obrigatório!");
        }

        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new CampoInvalidoexception("Processo não encontrado!"));

        Path caminhoArquivo = salvarArquivoNoDisco(arquivo);

        Documento documento = new Documento();
        atualizarPropriedadesDocumento(documento, arquivo, caminhoArquivo);
        documento.setProcesso(processo);

        Documento salvo = documentoRepository.save(documento);
        return converterDTO(salvo);
    }

    // LISTAR DOCUMENTOS
    @Transactional(readOnly = true)
    public List<DocumentoDTO> listarDocumentos() {
        return documentoRepository.findAll()
                .stream()
                .map(this::converterDTO)
                .toList();
    }

    // BUSCAR POR ID
    @Transactional(readOnly = true)
    public DocumentoDTO buscarPorId(Long id) {
        Documento documento = buscarDocumentoEntidadePorId(id);
        return converterDTO(documento);
    }

    // ATUALIZAR DOCUMENTO (SUBSTITUINDO O ARQUIVO FÍSICO)
    public DocumentoDTO atualizarDocumento(Long id, MultipartFile novoArquivo, Integer novoProcessoId) throws IOException {
        Documento documento = buscarDocumentoEntidadePorId(id);

        if (novoArquivo != null && !novoArquivo.isEmpty()) {
            // Exclui o arquivo físico antigo
            excluirArquivoFisico(documento.getCaminho());

            // Salva o novo arquivo
            Path novoCaminho = salvarArquivoNoDisco(novoArquivo);

            // Atualiza metadados do documento
            atualizarPropriedadesDocumento(documento, novoArquivo, novoCaminho);
        }

        if (novoProcessoId != null) {
            Processo processo = processoRepository.findById(novoProcessoId)
                    .orElseThrow(() -> new CampoInvalidoexception("Processo informado não encontrado!"));
            documento.setProcesso(processo);
        }

        Documento atualizado = documentoRepository.save(documento);
        return converterDTO(atualizado);
    }

    // EXCLUIR DOCUMENTO
    public void excluirDocumento(Long id) throws IOException {
        Documento documento = buscarDocumentoEntidadePorId(id);

        excluirArquivoFisico(documento.getCaminho());
        documentoRepository.delete(documento);
    }

    // MÉTODOS AUXILIARES PRIVADOS

    private Documento buscarDocumentoEntidadePorId(Long id) {
        if (id == null) {
            throw new CampoInvalidoexception("ID do documento é obrigatório!");
        }
        return documentoRepository.findById(id)
                .orElseThrow(() -> new CampoInvalidoexception("Documento não encontrado com o ID: " + id));
    }

    private void validarArquivo(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new CampoInvalidoexception("O arquivo enviado é obrigatório e não pode estar vazio!");
        }
    }

    private Path salvarArquivoNoDisco(MultipartFile arquivo) throws IOException {
        Path diretorio = Paths.get(pastaUpload);
        if (!Files.exists(diretorio)) {
            Files.createDirectories(diretorio);
        }

        String nomeUnico = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();
        Path caminho = diretorio.resolve(nomeUnico);

        Files.write(caminho, arquivo.getBytes());
        return caminho;
    }

    private void excluirArquivoFisico(String caminho) throws IOException {
        if (caminho != null && !caminho.isBlank()) {
            Path arquivo = Paths.get(caminho);
            Files.deleteIfExists(arquivo);
        }
    }

    private void atualizarPropriedadesDocumento(Documento documento, MultipartFile arquivo, Path caminho) {
        documento.setNome(arquivo.getOriginalFilename());
        documento.setTipo(arquivo.getContentType());
        documento.setTamanho(arquivo.getSize());
        documento.setCaminho(caminho.toString());
    }

    private DocumentoDTO converterDTO(Documento documento) {
        return new DocumentoDTO(
                documento.getId(),
                documento.getNome(),
                documento.getTipo(),
                documento.getTamanho(),
                documento.getCaminho()
        );
    }

    // LISTAR DOCUMENTOS POR PROCESSO
@Transactional(readOnly = true)
public List<DocumentoDTO> listarDocumentosPorProcesso(Integer processoId) {

    if (processoId == null) {
        throw new CampoInvalidoexception("ID do processo é obrigatório!");
    }

    Processo processo = processoRepository.findById(processoId)
            .orElseThrow(() -> new CampoInvalidoexception("Processo não encontrado!"));

    return documentoRepository.findByProcesso(processo)
            .stream()
            .map(this::converterDTO)
            .toList();
}
}