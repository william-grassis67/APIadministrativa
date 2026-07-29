package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Processo;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Integer> {

    List<Processo> findAllByUsuarioId(Integer usuarioId);

}