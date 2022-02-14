package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.UsuarioOutput;
import com.ifoodapi.domain.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioOutput toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioOutput.class);
    }

    public List<UsuarioOutput> toCollectionModel(Collection<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toModel(usuario))
                .collect(Collectors.toList());
    }
}
