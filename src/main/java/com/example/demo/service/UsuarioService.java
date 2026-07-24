package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.entity.GuiasInss;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.GuiasInssRepository;


@Service
public class UsuarioService {


    private final GuiasInssRepository guiasInssRepository;
    private final ClienteRepository clienteRepository;



    public UsuarioService(
            GuiasInssRepository guiasInssRepository,
            ClienteRepository clienteRepository
    ) {

        this.guiasInssRepository = guiasInssRepository;
        this.clienteRepository = clienteRepository;

    }



    /**
     * Criar nova guia INSS
     */
    public GuiasInss criarGuia(
            Integer usuarioId,
            GuiasInss guiaInss
    ) {


        Usuario usuario = clienteRepository.findById(usuarioId)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );



        if (guiaInss.getCompetencia() == null ||
            guiaInss.getCompetencia().isBlank()) {

            throw new RuntimeException(
                    "Competência obrigatória"
            );

        }



        if (guiaInss.getValor() == null ||
            guiaInss.getValor() <= 0) {

            throw new RuntimeException(
                    "Valor inválido"
            );

        }



        // Vincula usuário
        guiaInss.setUsuario(usuario);



        // Nova guia começa pendente
        guiaInss.setPaga(false);



        // Ainda não foi paga
        guiaInss.setDataPagamento(null);



        return guiasInssRepository.save(guiaInss);

    }





    /**
     * Confirmar pagamento de uma guia
     */
    public GuiasInss confirmarPagamento(
            Integer guiaId
    ) {


        GuiasInss guia =
                guiasInssRepository.findById(guiaId)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Guia não encontrada"
                        )
                );



        if (guia.isPaga()) {

            throw new RuntimeException(
                    "Esta guia já foi paga"
            );

        }



        guia.setPaga(true);


        guia.setDataPagamento(
                LocalDateTime.now()
        );



        return guiasInssRepository.save(guia);

    }

}