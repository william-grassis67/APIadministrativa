package com.example.demo.repository;

import com.example.demo.entity.Pagamento;
import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    Optional<Pagamento> findTopByUsuarioOrderByIdDesc(Usuario usuario);
}
