package com.ifoodapi.domain.repository;

import com.ifoodapi.domain.entity.Usuario;

public interface UsuarioCustomRepository {
    void detach(Usuario usuario);
}
