package com.ifoodapi.domain.repository;

import com.ifoodapi.domain.entity.FotoProduto;

public interface ProdutoCustomRepository {

    FotoProduto save(FotoProduto fotoProduto);

    void delete(FotoProduto fotoProduto);
}
