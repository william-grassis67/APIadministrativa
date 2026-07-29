package com.example.demo.service;

import com.example.demo.entity.Documento;
import com.example.demo.entity.Processo;
import com.example.demo.repository.DocumentoRepository;
import com.example.demo.repository.ProcessoRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ProcessoRepository processoRepository;


    public DocumentoService(
            DocumentoRepository documentoRepository,
            ProcessoRepository processoRepository) {

        this.documentoRepository = documentoRepository;
        this.processoRepository = processoRepository;
    }



    // Salvar documento (imagem/PDF)
    public Documento salvarDocumento(
            MultipartFile arquivo,
            Integer processoId
    ) throws IOException {


        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() ->
                        new RuntimeException("Processo não encontrado!")
                );


        Documento documento = new Documento();

        documento.setNome(
                arquivo.getOriginalFilename()
        );

        documento.setTipo(
                arquivo.getContentType()
        );

        documento.setTamanho(
                arquivo.getSize()
        );

        documento.setDados(
                arquivo.getBytes()
        );

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
                .orElseThrow(() ->
                        new RuntimeException("Documento não encontrado!")
                );
    }



    // Atualizar documento
    public Documento atualizarDocumento(
            Long id,
            MultipartFile arquivo
    ) throws IOException {


        Documento documento = buscarPorId(id);


        if (arquivo != null && !arquivo.isEmpty()) {

            documento.setNome(
                    arquivo.getOriginalFilename()
            );

            documento.setTipo(
                    arquivo.getContentType()
            );

            documento.setTamanho(
                    arquivo.getSize()
            );

            documento.setDados(
                    arquivo.getBytes()
            );
        }


        return documentoRepository.save(documento);
    }



    // Excluir documento
    public void excluirDocumento(Long id) {

        Documento documento = buscarPorId(id);

        documentoRepository.delete(documento);
    }
}