package com.example.demo;

import com.example.demo.entity.Pagamento;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.PagamentoRepository;
import com.example.demo.service.PagamentoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PagamentoServiceTest {

    @Test
    void shouldStorePendingPaymentWithoutDate() {
        PagamentoRepository repository = mock(PagamentoRepository.class);
        PagamentoService service = new PagamentoService(repository);

        Usuario usuario = new Usuario();
        usuario.setId(10);

        Pagamento pagamentoRecebido = new Pagamento();
        pagamentoRecebido.setPago(false);
        pagamentoRecebido.setUsuario(usuario);

        when(repository.save(any(Pagamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pagamento resultado = service.registrarPagamento(pagamentoRecebido);

        assertFalse(resultado.isPago());
        assertNull(resultado.getDataPagamento());
        assertEquals(usuario, resultado.getUsuario());
    }
}
