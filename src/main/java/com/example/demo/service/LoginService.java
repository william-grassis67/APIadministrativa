package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.CampoInvalidoexception;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.GuiasInssRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginService {

    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;
    private final GuiasInssRepository guiasInssRepository;


    public LoginService(
            ClienteRepository clienteRepository,
            JwtService jwtService,
            GuiasInssRepository guiasInssRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.jwtService = jwtService;
        this.guiasInssRepository = guiasInssRepository;
    }



    public LoginDTO login(String cpf, String senha) {


        System.out.println("CPF recebido: [" + cpf + "]");
        System.out.println("Senha recebida: [" + senha + "]");


        Usuario usuario = clienteRepository.findByCpf(cpf)
                .orElseThrow(() ->
                        new CampoInvalidoexception(
                                "CPF ou senha inválidos!"
                        )
                );


        System.out.println("Usuário encontrado: " + usuario.getNome());
        System.out.println("Senha no banco: [" + usuario.getSenha() + "]");



        if (usuario.getSenha() == null ||
                !usuario.getSenha().equals(senha)) {

            throw new CampoInvalidoexception(
                    "CPF ou senha inválidos!"
            );
        }



        String token = jwtService.gerarToken(usuario);



        usuario.setUltimoAcesso(LocalDateTime.now());

        boolean acessoLiberado = true;

        boolean pagamentoPago = false;



        clienteRepository.save(usuario);



        return new LoginDTO(
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipo(),
                usuario.getUltimoAcesso(),
                usuario.getGuiasInsses(),
                null,                         // mensagemPagamento
                usuario.getNumeroTelefone(),  // numeroTelefone
                pagamentoPago,                // pagamentoPago
                null,                         // statusPagamento
                null,                         // dataPagamento
                acessoLiberado,
                token,
                usuario.getId()
        );
    }
}