package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.Produto;
import com.ifoodapi.domain.entity.Restaurante;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteService restauranteService;

    public Produto findById(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.PRODUTO_NAO_ENCONTRADO.getMensagem(), produtoId)));
    }

    public List<Produto> findAtivosByRestaurante(Restaurante restaurante) {
        return produtoRepository.findAtivosByRestaurante(restaurante);
    }

    public List<Produto> findAllByRestaurante(Restaurante restaurante) {
        return produtoRepository.findAllByRestaurante(restaurante);
    }

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }
}
