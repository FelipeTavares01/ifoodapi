package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.ProdutoOutput;
import com.ifoodapi.domain.entity.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoOutput toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoOutput.class);
    }

    public List<ProdutoOutput> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toModel(produto))
                .collect(Collectors.toList());
    }
}
