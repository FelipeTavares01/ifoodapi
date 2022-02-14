package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.input.UsuarioInput;
import com.ifoodapi.domain.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEntityAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toEntity(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToEntity(Object object, Usuario usuario) {
        modelMapper.map(object, usuario);
    }
}
