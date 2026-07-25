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

        final String cpfLimpo = cpf.replaceAll("\\D", "").trim();

        System.out.println("====================================");
        System.out.println("INÍCIO DO LOGIN");
        System.out.println("CPF recebido: [" + cpfLimpo + "]");
        System.out.println("Senha recebida: [" + senha + "]");
        System.out.println("Quantidade de usuários: " + clienteRepository.count());

        clienteRepository.findAll().forEach(u -> {
            System.out.println(
                    "ID: " + u.getId()
                            + " | Nome: " + u.getNome()
                            + " | CPF: [" + u.getCpf() + "]"
            );
        });

        Usuario usuario = clienteRepository.findByCpf(cpfLimpo)
                .orElseThrow(() -> {
                    System.out.println("Nenhum usuário encontrado com o CPF: " + cpfLimpo);
                    return new CampoInvalidoexception("CPF ou senha inválidos!");
                });

        System.out.println("Usuário encontrado: " + usuario.getNome());
        System.out.println("CPF banco: [" + usuario.getCpf() + "]");
        System.out.println("Senha banco: [" + usuario.getSenha() + "]");

        if (usuario.getSenha() == null || !usuario.getSenha().equals(senha)) {

            System.out.println("Senha incorreta!");

            throw new CampoInvalidoexception("CPF ou senha inválidos!");
        }

        String token = jwtService.gerarToken(usuario);

        usuario.setUltimoAcesso(LocalDateTime.now());

        clienteRepository.save(usuario);

        System.out.println("Login realizado com sucesso!");
        System.out.println("====================================");

        return new LoginDTO(
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipo(),
                usuario.getUltimoAcesso(),
                usuario.getGuiasInsses(),
                null,
                usuario.getNumeroTelefone(),
                false,
                null,
                null,
                true,
                token,
                usuario.getId()
        );
    }
}