package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.entity.GuiasInss;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.GuiasInssRepository;

@Service
public class UsuarioService {
    private final GuiasInssRepository pagamGuiasInss;
    private final ClienteRepository clienteRepository;
    
    public UsuarioService(GuiasInssRepository pagamGuiasInss, ClienteRepository clienteRepository){
        this.pagamGuiasInss = pagamGuiasInss;
        this.clienteRepository = clienteRepository;
    }

    //PAGAR GUIA DO GuiasInss
    public GuiasInss paymentRegister(Integer usuarioId,GuiasInss guiasInss){
        Usuario usuario = clienteRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

            guiasInss.setUsuario(usuario);
            //guiasInss.setId(usuario.getId());
            //guiasInss.setCpf(usuario.getCpf());
            guiasInss.setDataPagamento(LocalDateTime.now());
            //guiasInss.setValor(guiasInss.getValor());
            
            

            return guiasInss;
    }
}
