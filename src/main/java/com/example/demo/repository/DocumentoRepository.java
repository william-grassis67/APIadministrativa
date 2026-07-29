package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long>{

}
