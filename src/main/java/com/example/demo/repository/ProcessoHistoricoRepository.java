package com.example.demo.repository;

import com.example.demo.entity.Processo;
import com.example.demo.entity.ProcessoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessoHistoricoRepository extends JpaRepository<ProcessoHistorico, Integer> {
    List<ProcessoHistorico> findAllByProcessoOrderByDataHoraAsc(Processo processo);
}
