package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.EstadoOutput;
import com.ifoodapi.domain.entity.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoOutput toModel(Estado estado) {
        return modelMapper.map(estado, EstadoOutput.class);
    }

    public List<EstadoOutput> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(estado -> toModel(estado))
                .collect(Collectors.toList());
    }
}
