package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.GrupoOutput;
import com.ifoodapi.domain.entity.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoOutput toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoOutput.class);
    }

    public List<GrupoOutput> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toModel(grupo))
                .collect(Collectors.toList());
    }
}
