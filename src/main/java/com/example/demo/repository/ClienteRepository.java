package com.example.demo.repository;

import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE TRIM(u.cpf) = :cpf")
    Optional<Usuario> findByCpf(@Param("cpf") String cpf);


    List<Usuario> findByTipo(Usuario.TipoUsuario tipo);


    Optional<Usuario> findByEmail(String email);

}