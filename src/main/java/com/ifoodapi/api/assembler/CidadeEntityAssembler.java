package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.input.CidadeInput;
import com.ifoodapi.domain.entity.Cidade;
import com.ifoodapi.domain.entity.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeEntityAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toEntity(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToEntity(CidadeInput cidadeInput, Cidade cidade) {
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }
}
