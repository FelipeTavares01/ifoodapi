package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.input.ProdutoInput;
import com.ifoodapi.domain.entity.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoEntityAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toEntity(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToEntity(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }
}
