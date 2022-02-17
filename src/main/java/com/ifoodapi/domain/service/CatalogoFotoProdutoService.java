package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.FotoProduto;
import com.ifoodapi.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto save(FotoProduto fotoProduto) {

        Long restauranteId = fotoProduto.getProduto().getRestaurante().getId();
        Long produtoId = fotoProduto.getProduto().getId();

        Optional<FotoProduto> fotoProdutoOptional = produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoProdutoOptional.isPresent()) {
            produtoRepository.delete(fotoProdutoOptional.get());
        }

        return produtoRepository.save(fotoProduto);
    }
}
