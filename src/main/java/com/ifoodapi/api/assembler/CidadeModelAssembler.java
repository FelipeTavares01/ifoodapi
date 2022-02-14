package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.CidadeOutput;
import com.ifoodapi.domain.entity.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeOutput toModel(Cidade cidade) {
        return  modelMapper.map(cidade, CidadeOutput.class);
    }

    public List<CidadeOutput> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }
}
