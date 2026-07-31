package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.CampoInvalidoexception;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.GuiasInssRepository;

@Service
@Transactional
public class LoginService {

    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;
    private final GuiasInssRepository guiasInssRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(
            ClienteRepository clienteRepository,
            JwtService jwtService,
            GuiasInssRepository guiasInssRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.clienteRepository = clienteRepository;
        this.jwtService = jwtService;
        this.guiasInssRepository = guiasInssRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginDTO login(String cpf, String senha) {

        if (cpf == null || cpf.isBlank()) {
            throw new CampoInvalidoexception("CPF é obrigatório!");
        }

        if (senha == null || senha.isBlank()) {
            throw new CampoInvalidoexception("Senha é obrigatória!");
        }

        final String cpfLimpo = cpf.replaceAll("\\D", "").trim();

        // 1. Busca usuário pelo CPF
        Usuario usuario = clienteRepository.findByCpf(cpfLimpo)
                .orElseThrow(() -> new CampoInvalidoexception("CPF ou senha inválidos!"));

        // 2. Valida a senha usando o PasswordEncoder (BCrypt)
        if (usuario.getSenha() == null || !passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new CampoInvalidoexception("CPF ou senha inválidos!");
        }

        // 3. Atualiza e persiste a data do último acesso no banco de dados
        usuario.setUltimoAcesso(LocalDateTime.now());
        usuario = clienteRepository.save(usuario);

        // 4. Gera o Token JWT
        String token = jwtService.gerarToken(usuario);

        // 5. Retorna o DTO preenchido
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