package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.ClienteRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(ClienteRepository clienteRepository,
                            PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (clienteRepository.findByEmail("admin@sapi.com").isPresent()) {
            return;
        }

        Usuario admin = new Usuario();
        admin.setNome("Lavinia Souto");
        admin.setEmail("admin@sapi.com");
        admin.setEndereco("teste-teste");
        admin.setCpf("16322804774");
        admin.setNumeroTelefone("00000000000");
        admin.setSenha(passwordEncoder.encode("tatu123"));
        admin.setTipo(Usuario.TipoUsuario.ADMIN);

        clienteRepository.save(admin);

        System.out.println("Administrador padrão criado com sucesso.");
    }
}