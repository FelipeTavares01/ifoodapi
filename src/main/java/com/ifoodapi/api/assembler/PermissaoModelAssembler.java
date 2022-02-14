package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.PermissaoOutput;
import com.ifoodapi.domain.entity.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoOutput toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoOutput.class);
    }

    public List<PermissaoOutput> toCollectionModel(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(permissao -> toModel(permissao))
                .collect(Collectors.toList());
    }
}
