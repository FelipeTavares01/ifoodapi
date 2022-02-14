package com.ifoodapi.domain.repository.impl;

import com.ifoodapi.domain.entity.Usuario;
import com.ifoodapi.domain.repository.UsuarioCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UsuarioCustomRepositoryImpl implements UsuarioCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void detach(Usuario usuario) {
        entityManager.detach(usuario);
    }
}
