package com.example.demo.controller;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.service.AdminService;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Pagamento;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    // CADASTRAR CLIENTE
    @PostMapping("/register")
    public UsuarioDTO registerUser(@RequestBody UsuarioDTO usuarioDTO) {

        return adminService.registerUser(
                usuarioDTO.getNome(),
                usuarioDTO.getEmail(),
                usuarioDTO.getEndereco(),
                usuarioDTO.getCpf(),
                usuarioDTO.getSenha()
        );
    }


    // REMOVER CLIENTE
    @DeleteMapping("/remove/{cpf}")
    public void removeUser(@PathVariable String cpf) {

        adminService.removeUser(cpf);
    }


    // LISTAR TODOS OS USUÁRIOS
    @GetMapping("/users")
    public List<Usuario> listUsers(@RequestParam(required = false) String cpf) {
        if (cpf != null && !cpf.isBlank()) {
            adminService.validarAcessoAdministrador(cpf);
        }
        return adminService.listUsers();
    }


    // LISTAR APENAS CLIENTES
    @GetMapping("/clientes")
    public List<Usuario> listClientes(@RequestParam(required = false) String cpf) {
        if (cpf != null && !cpf.isBlank()) {
            adminService.validarAcessoAdministrador(cpf);
        }
        return adminService.listClientes();
    }

    // LISTAR PAGAMENTOS
    @GetMapping("/payments")
    public List<Pagamento> listPagamentos(@RequestParam(required = false) String cpf) {
        if (cpf != null && !cpf.isBlank()) {
            adminService.validarAcessoAdministrador(cpf);
        }
        return adminService.listarPagamentos();
    }
    

    @GetMapping("/payments/user/{id}")
    public Pagamento listPagamentosByUserId(@PathVariable Integer id) {
        return adminService.buscarPagamentoPorId(id);
    }
}