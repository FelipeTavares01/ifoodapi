package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.FotoProdutoOutput;
import com.ifoodapi.domain.entity.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoOutput toModel(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoOutput.class);
    }
}
