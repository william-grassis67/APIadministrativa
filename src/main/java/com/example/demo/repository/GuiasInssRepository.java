package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GuiasInss;
import com.example.demo.entity.Usuario;

@Repository
public interface GuiasInssRepository extends JpaRepository<GuiasInss, Integer> {

    Optional<GuiasInss> findTopByUsuarioOrderByDatapagamentoDesc(Usuario usuario);

}