package com.example.demo.controller;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.service.AdminService;
import com.example.demo.service.UsuarioService;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.GuiasInss;
//import com.example.demo.entity.Pagamento;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;
    private final UsuarioService usuarioService;

    public AdminController(AdminService adminService, UsuarioService usuarioService) {
        this.adminService = adminService;
        this.usuarioService = usuarioService;
    }

    // CADASTRAR CLIENTE
    @PostMapping("/register")
    public UsuarioDTO registerUser(@RequestBody UsuarioDTO usuarioDTO) {

        return adminService.registerUser(
                usuarioDTO.getNome(),
                usuarioDTO.getEmail(),
                usuarioDTO.getEndereco(),
                usuarioDTO.getCpf(),
                usuarioDTO.getSenha(),
                usuarioDTO.getNumeroTelefone());
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

    @GetMapping("/payments/guias/{id}")
    public GuiasInss listPagamento(@PathVariable Integer id) {

        return adminService.findByGuia(id);
    }  

    @GetMapping("/payments/guias")
    public List<GuiasInss> listPaymentsinss(){
        return adminService.listarPagamentos();
    }

    //ULTIMO PAGAMENTO
    @GetMapping("/payments/last-payment")
    public GuiasInss lastPayments(Usuario usuario){
        return adminService.lastPayments(usuario);
    }
}