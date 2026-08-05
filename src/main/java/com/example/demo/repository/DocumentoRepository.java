package com.example.demo.repository;

import com.example.demo.entity.Documento;
import com.example.demo.entity.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

List<Documento> findByProcesso(Processo processo);

}
