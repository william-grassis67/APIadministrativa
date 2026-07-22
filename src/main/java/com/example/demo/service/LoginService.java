package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.GuiasInss;
//import com.example.demo.entity.Pagamento;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.CampoInvalidoexception;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.GuiasInssRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class LoginService {

    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;
    //private final PagamentoService pagamentoService;
    private final GuiasInssRepository guiasInssRepository;

    public LoginService(ClienteRepository clienteRepository, JwtService jwtService, GuiasInssRepository guiasInssRepository) {
        this.clienteRepository = clienteRepository;
        this.jwtService = jwtService;
        this.guiasInssRepository = guiasInssRepository;
    }

    Date date = new Date();

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
        clienteRepository.save(usuario);

        //Pagamento pagamento = pagamentoService.buscarUltimoPagamento(usuario);
        //boolean pagamentoPago = pagamentoService.isPagamentoPago(usuario);
        //String statusPagamento = pagamentoService.getStatusPagamento(usuario);
        //LocalDateTime dataPagamento = pagamento != null ? pagamento.getDataPagamento() : null;
        //String mensagemPagamento = pagamentoPago ? "Pagamento confirmado." : "Pagamento pendente";
        //boolean acessoLiberado = pagamentoPago || usuario.getTipo() != Usuario.TipoUsuario.ADMIN;
        return new LoginDTO(
        usuario.getCpf(),
        usuario.getNome(),
        usuario.getEmail(),
        usuario.getTipo(),
        usuario.getUltimoAcesso(),
        usuario.getGuiasInsses(),
        usuario.getMensagemPagamento(),
        usuario.getNumeroTelefone(),
        usuario.isPagamentoPago(),
        usuario.getStatusPagamento(),
        usuario.getDataPagamento(),
        true,
        token
);
    }
}

