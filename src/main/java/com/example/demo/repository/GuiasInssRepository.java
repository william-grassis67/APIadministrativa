package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.GuiasInss;

public interface GuiasInssRepository extends JpaRepository<GuiasInss, Integer> {

    List<GuiasInss> findByUsuarioId(Integer usuarioId);

}