package com.example.demo.service;

import com.example.demo.entity.Documento;
import com.example.demo.entity.Processo;
import com.example.demo.repository.DocumentoRepository;
import com.example.demo.repository.ProcessoRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ProcessoRepository processoRepository;


    private final String pastaUpload = "uploads";


    public DocumentoService(
            DocumentoRepository documentoRepository,
            ProcessoRepository processoRepository) {

        this.documentoRepository = documentoRepository;
        this.processoRepository = processoRepository;
    }



    // Salvar documento
    public Documento salvarDocumento(
            MultipartFile arquivo,
            Integer processoId
    ) throws IOException {


        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() ->
                        new RuntimeException("Processo não encontrado!")
                );


        // cria pasta caso não exista
        Path diretorio = Paths.get(pastaUpload);

        if (!Files.exists(diretorio)) {
            Files.createDirectories(diretorio);
        }


        // cria nome único para evitar conflito
        String nomeArquivo =
                UUID.randomUUID()
                + "_"
                + arquivo.getOriginalFilename();


        Path caminhoArquivo = diretorio.resolve(nomeArquivo);


        // salva arquivo no servidor
        Files.write(
                caminhoArquivo,
                arquivo.getBytes()
        );


        Documento documento = new Documento();


        documento.setCaminho(
                caminhoArquivo.toString()
        );


        documento.setNome(
                arquivo.getOriginalFilename()
        );


        documento.setTipo(
                arquivo.getContentType()
        );


        documento.setTamanho(
                arquivo.getSize()
        );


        documento.setProcesso(processo);


        return documentoRepository.save(documento);
    }



    // Buscar todos documentos
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


            Path diretorio = Paths.get(pastaUpload);

            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }


            String nomeArquivo =
                    UUID.randomUUID()
                    + "_"
                    + arquivo.getOriginalFilename();


            Path caminhoArquivo =
                    diretorio.resolve(nomeArquivo);


            Files.write(
                    caminhoArquivo,
                    arquivo.getBytes()
            );


            documento.setCaminho(
                    caminhoArquivo.toString()
            );


            documento.setNome(
                    arquivo.getOriginalFilename()
            );


            documento.setTipo(
                    arquivo.getContentType()
            );


            documento.setTamanho(
                    arquivo.getSize()
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