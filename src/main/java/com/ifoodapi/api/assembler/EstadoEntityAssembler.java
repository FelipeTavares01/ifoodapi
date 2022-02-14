package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.input.EstadoInput;
import com.ifoodapi.domain.entity.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoEntityAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toEntity(EstadoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyToEntity(EstadoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }
}
