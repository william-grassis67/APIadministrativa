package com.example.demo.repository;

import com.example.demo.entity.Processo;
import com.example.demo.entity.ProcessoMensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessoMensagemRepository extends JpaRepository<ProcessoMensagem, Integer> {
    List<ProcessoMensagem> findAllByProcessoOrderByDataHoraAsc(Processo processo);
    long countByProcessoAndStatus(Processo processo, ProcessoMensagem.StatusMensagem status);
}
