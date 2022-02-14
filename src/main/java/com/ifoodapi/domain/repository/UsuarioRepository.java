package com.ifoodapi.domain.repository;

import com.ifoodapi.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioCustomRepository {

    Optional<Usuario> findByEmail(String email);
}
