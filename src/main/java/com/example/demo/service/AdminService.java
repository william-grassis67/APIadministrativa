package com.example.demo.service;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.GuiasInss;
//import com.example.demo.entity.Pagamento;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.CampoInvalidoexception;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.GuiasInssRepository;
//import com.example.demo.repository.PagamentoRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminService {

        private final ClienteRepository clienteRepository;
        private final GuiasInssRepository guiasInssRepository;

        public AdminService(
                        ClienteRepository clienteRepository,
                        // PagamentoRepository pagamentoRepository,
                        // PagamentoService pagamentoService,
                        GuiasInssRepository guiasInssRepository) {
                this.clienteRepository = clienteRepository;
                // this.pagamentoRepository = pagamentoRepository;
                // this.pagamentoService = pagamentoService;
                this.guiasInssRepository = guiasInssRepository;
        }

        // CADASTRAR CLIENTE
        public UsuarioDTO registerUser(
                        String nome,
                        String email,
                        String endereco,
                        String cpf,
                        String senha,
                        Integer numeroTelefone) {

                if (clienteRepository.findByEmail(email).isPresent()) {
                        throw new CampoInvalidoexception(
                                        "Email já cadastrado!");
                }

                if (clienteRepository.findByCpf(cpf).isPresent()) {
                        throw new CampoInvalidoexception(
                                        "CPF já cadastrado!");
                }

                Usuario usuario = new Usuario();

                if (usuario.getNumeroTelefone() != 11) {
                        throw new CampoInvalidoexception("O numero precisa ter exatamente 11 digitos");
                }

                usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setEndereco(endereco);
                usuario.setCpf(cpf);
                usuario.setSenha(senha);
                usuario.setNumeroTelefone(numeroTelefone);

                // Cadastro realizado pelo admin sempre cria cliente
                usuario.setTipo(Usuario.TipoUsuario.CLIENTE);

                Usuario usuarioSalvo = clienteRepository.save(usuario);

                return new UsuarioDTO(
                                usuarioSalvo.getId(),
                                usuarioSalvo.getNome(),
                                usuarioSalvo.getEmail(),
                                usuarioSalvo.getEndereco(),
                                usuarioSalvo.getCpf(),
                                usuarioSalvo.getNumeroTelefone());
        }

        // DELETAR USUÁRIO PELO CPF
        public void removeUser(String cpf) {

                Usuario usuario = clienteRepository.findByCpf(cpf)
                                .orElseThrow(() -> new CampoInvalidoexception(
                                                "Usuário não encontrado!"));

                clienteRepository.deleteById(
                                Objects.requireNonNull(
                                                usuario.getId(),
                                                "ID do usuário é obrigatório"));
        }

        // LISTAR TODOS OS USUÁRIOS
        public List<Usuario> listUsers() {

                List<Usuario> usuarios = clienteRepository.findAll();
                return usuarios;
        }

        // VALIDAR ACESSO ADMIN
        public void validarAcessoAdministrador(String cpf) {

                Usuario usuario = clienteRepository.findByCpf(cpf)
                                .orElseThrow(() -> new CampoInvalidoexception(
                                                "Usuário não encontrado!"));

                if (usuario.getTipo() != Usuario.TipoUsuario.ADMIN) {

                        throw new CampoInvalidoexception(
                                        "Acesso restrito a administradores.");
                }
        }

        // LISTAR SOMENTE CLIENTES
        public List<Usuario> listClientes() {
                return clienteRepository.findAll();
        }

        // BUSCAR USUÁRIO PELO EMAIL
        public Usuario buscarPorEmail(String email) {

                Usuario usuario = clienteRepository.findByEmail(email)
                                .orElseThrow(() -> new CampoInvalidoexception(
                                                "Usuário não encontrado!"));

                return usuario;
        }

        // LISTAR PAGAMENTOS
        public List<GuiasInss> listarPagamentos() {

                return guiasInssRepository.findAll();
        }

        // BUSCAR PAGAMENTO PELO ID
        public GuiasInss buscarPagamentoPorId(Integer id) {

                return guiasInssRepository.findById(
                                Objects.requireNonNull(
                                                id,
                                                "ID do pagamento é obrigatório"))
                                .orElseThrow(() -> new CampoInvalidoexception(
                                                "Pagamento não encontrado!"));
        }

        // PEGAR GUIA DO Usuario
        public GuiasInss findByGuia(Integer id) {

                return guiasInssRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Guia não encontrada"));

        }

        // BUSCAR ÚLTIMO PAGAMENTO
        public GuiasInss lastPayments(Usuario usuario) {

                return guiasInssRepository
                                .findTopByUsuarioOrderByDatapagamentoDesc(usuario)
                                .orElseThrow(() -> new RuntimeException("Nenhum pagamento encontrado"));
        }
}