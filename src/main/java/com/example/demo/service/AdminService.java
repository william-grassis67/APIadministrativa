package com.example.demo.service;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Pagamento;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.CampoInvalidoexception;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.PagamentoRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminService {

    private final ClienteRepository clienteRepository;
    private final PagamentoRepository pagamentoRepository;
    private final PagamentoService pagamentoService;

    public AdminService(
            ClienteRepository clienteRepository,
            PagamentoRepository pagamentoRepository,
            PagamentoService pagamentoService
    ) {
        this.clienteRepository = clienteRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoService = pagamentoService;
    }


    // CRIA ADMIN PADRÃO APÓS O SISTEMA INICIAR
    @EventListener(ApplicationReadyEvent.class)
    public void criarAdmin() {

        if (clienteRepository.findByTipo(Usuario.TipoUsuario.ADMIN).isEmpty()) {

            Usuario admin = new Usuario();

            admin.setNome("Administrador");
            admin.setEmail("admin@empresa.com");
            admin.setEndereco("Sistema");
            admin.setCpf("00000000000");

            // Futuramente trocar por BCrypt
            admin.setSenha("123456");

            admin.setTipo(Usuario.TipoUsuario.ADMIN);

            clienteRepository.save(admin);

            System.out.println("Administrador padrão criado!");
        }
    }


    // CADASTRAR CLIENTE
    public UsuarioDTO registerUser(
            String nome,
            String email,
            String endereco,
            String cpf,
            String senha
    ) {

        if (clienteRepository.findByEmail(email).isPresent()) {
            throw new CampoInvalidoexception(
                    "Email já cadastrado!"
            );
        }

        if (clienteRepository.findByCpf(cpf).isPresent()) {
            throw new CampoInvalidoexception(
                    "CPF já cadastrado!"
            );
        }


        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setEndereco(endereco);
        usuario.setCpf(cpf);
        usuario.setSenha(senha);

        // Cadastro realizado pelo admin sempre cria cliente
        usuario.setTipo(Usuario.TipoUsuario.CLIENTE);


        Usuario usuarioSalvo = clienteRepository.save(usuario);


        return new UsuarioDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getEndereco(),
                usuarioSalvo.getCpf()
        );
    }



    // DELETAR USUÁRIO PELO CPF
    public void removeUser(String cpf) {

        Usuario usuario = clienteRepository.findByCpf(cpf)
                .orElseThrow(() ->
                        new CampoInvalidoexception(
                                "Usuário não encontrado!"
                        )
                );


        clienteRepository.deleteById(
                Objects.requireNonNull(
                        usuario.getId(),
                        "ID do usuário é obrigatório"
                )
        );
    }



    // LISTAR TODOS OS USUÁRIOS
    public List<Usuario> listUsers() {

        List<Usuario> usuarios = clienteRepository.findAll();

        usuarios.forEach(this::preencherDadosPagamento);

        return usuarios;
    }



    // VALIDAR ACESSO ADMIN
    public void validarAcessoAdministrador(String cpf) {

        Usuario usuario = clienteRepository.findByCpf(cpf)
                .orElseThrow(() ->
                        new CampoInvalidoexception(
                                "Usuário não encontrado!"
                        )
                );


        if (usuario.getTipo() != Usuario.TipoUsuario.ADMIN) {

            throw new CampoInvalidoexception(
                    "Acesso restrito a administradores."
            );
        }


        if (!pagamentoService.isPagamentoPago(usuario)) {

            throw new CampoInvalidoexception(
                    "Pagamento pendente. Acesso bloqueado até a confirmação do pagamento."
            );
        }
    }



    // LISTAR SOMENTE CLIENTES
    public List<Usuario> listClientes() {

        List<Usuario> usuarios =
                clienteRepository.findByTipo(
                        Usuario.TipoUsuario.CLIENTE
                );


        usuarios.forEach(this::preencherDadosPagamento);

        return usuarios;
    }



    // ADICIONA INFORMAÇÕES DE PAGAMENTO NO USUÁRIO
    private void preencherDadosPagamento(Usuario usuario) {

        Pagamento pagamento =
                pagamentoService.buscarUltimoPagamento(usuario);


        boolean pago =
                pagamento != null && pagamento.isPago();


        usuario.setPagamentoPago(pago);

        usuario.setStatusPagamento(
                pago ? "PAGO" : "PENDENTE"
        );


        usuario.setDataPagamento(
                pagamento != null
                        ? pagamento.getDataPagamento()
                        : null
        );


        usuario.setMensagemPagamento(
                pago
                        ? "Pagamento confirmado."
                        : "Pagamento pendente"
        );
    }



    // BUSCAR USUÁRIO PELO EMAIL
    public Usuario buscarPorEmail(String email) {

        Usuario usuario =
                clienteRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new CampoInvalidoexception(
                                        "Usuário não encontrado!"
                                )
                        );


        preencherDadosPagamento(usuario);

        return usuario;
    }



    // LISTAR PAGAMENTOS
    public List<Pagamento> listarPagamentos() {

        return pagamentoRepository.findAll();
    }



    // BUSCAR PAGAMENTO PELO ID
    public Pagamento buscarPagamentoPorId(Integer id) {

        return pagamentoRepository.findById(
                Objects.requireNonNull(
                        id,
                        "ID do pagamento é obrigatório"
                )
        )
        .orElseThrow(() ->
                new CampoInvalidoexception(
                        "Pagamento não encontrado!"
                )
        );
    }
}