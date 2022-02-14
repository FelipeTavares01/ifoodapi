package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.input.GrupoInput;
import com.ifoodapi.domain.entity.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoEntityAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toEntity(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToEntity(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
