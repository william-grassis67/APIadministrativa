package com.example.demo.service;

import com.example.demo.dto.DocumentoDTO;
import com.example.demo.entity.Documento;
import com.example.demo.entity.Processo;
import com.example.demo.repository.DocumentoRepository;
import com.example.demo.repository.ProcessoRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {


    private final DocumentoRepository documentoRepository;
    private final ProcessoRepository processoRepository;


    private final String pastaUpload = "uploads";



    public DocumentoService(
            DocumentoRepository documentoRepository,
            ProcessoRepository processoRepository
    ) {

        this.documentoRepository = documentoRepository;
        this.processoRepository = processoRepository;
    }



    // Salvar documento

    public DocumentoDTO salvarDocumento(
            MultipartFile arquivo,
            Integer processoId
    ) throws IOException {


        if (arquivo == null || arquivo.isEmpty()) {

            throw new RuntimeException(
                    "Arquivo obrigatório"
            );
        }



        Processo processo =
                processoRepository.findById(processoId)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Processo não encontrado!"
                        ));




        Path diretorio =
                Paths.get(pastaUpload);



        if (!Files.exists(diretorio)) {

            Files.createDirectories(diretorio);
        }



        String nomeArquivo =
                UUID.randomUUID()
                + "_"
                + arquivo.getOriginalFilename();



        Path caminho =
                diretorio.resolve(nomeArquivo);



        Files.write(
                caminho,
                arquivo.getBytes()
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


        documento.setCaminho(
                caminho.toString()
        );


        documento.setProcesso(processo);



        Documento salvo =
                documentoRepository.save(documento);



        return converterDTO(salvo);
    }




    // Listar documentos

    public List<DocumentoDTO> listarDocumentos() {


        return documentoRepository.findAll()

                .stream()

                .map(this::converterDTO)

                .toList();
    }




    // Buscar por ID

    public DocumentoDTO buscarPorId(Long id) {


        Documento documento =
                documentoRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Documento não encontrado!"
                        ));



        return converterDTO(documento);
    }





    // Excluir documento

    public void excluirDocumento(Long id)
            throws IOException {


        Documento documento =
                documentoRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Documento não encontrado!"
                        ));



        Path arquivo =
                Paths.get(documento.getCaminho());



        Files.deleteIfExists(arquivo);



        documentoRepository.delete(documento);
    }





    private DocumentoDTO converterDTO(
            Documento documento
    ) {


        return new DocumentoDTO(

                documento.getId(),

                documento.getNome(),

                documento.getTipo(),

                documento.getTamanho(),

                documento.getCaminho()
        );
    }

}