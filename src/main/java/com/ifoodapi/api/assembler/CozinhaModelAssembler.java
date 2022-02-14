package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.CozinhaOutput;
import com.ifoodapi.domain.entity.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaOutput toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaOutput.class);
    }

    public List<CozinhaOutput> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toModel(cozinha))
                .collect(Collectors.toList());
    }
}
