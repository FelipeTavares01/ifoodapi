package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.ProdutoEntityAssembler;
import com.ifoodapi.api.assembler.ProdutoModelAssembler;
import com.ifoodapi.api.model.input.ProdutoInput;
import com.ifoodapi.api.model.output.ProdutoOutput;
import com.ifoodapi.domain.entity.Produto;
import com.ifoodapi.domain.entity.Restaurante;
import com.ifoodapi.domain.service.ProdutoService;
import com.ifoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoEntityAssembler produtoEntityAssembler;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @GetMapping
    public ResponseEntity<List<ProdutoOutput>> findAll(@PathVariable Long restauranteId,
                                                       @RequestParam(required = false) boolean incluirInativos) {

        Restaurante restaurante = restauranteService.findById(restauranteId);

        List<Produto> produtos = null;

        if(incluirInativos) {
            produtos = produtoService.findAllByRestaurante(restaurante);
        } else {
             produtos = produtoService.findAtivosByRestaurante(restaurante);
        }

        List<ProdutoOutput> produtosOutputs = produtoModelAssembler.toCollectionModel(produtos);

        return ResponseEntity.ok(produtosOutputs);

    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutput> findById(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);

        Produto produto = produtoService.findById(restauranteId, produtoId);

        return ResponseEntity.ok(produtoModelAssembler.toModel(produto));
    }

    @PostMapping
    public ResponseEntity<ProdutoOutput> create(@PathVariable Long restauranteId,
                                                @RequestBody @Valid ProdutoInput produtoInput) {

        Restaurante restaurante = restauranteService.findById(restauranteId);

        Produto produto = produtoEntityAssembler.toEntity(produtoInput);

        produto.setRestaurante(restaurante);

        ProdutoOutput produtoOutput = produtoModelAssembler.toModel(produtoService.save(produto));

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoOutput);
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<ProdutoOutput> update(@PathVariable Long restauranteId,
                                                @PathVariable Long produtoId,
                                                @RequestBody @Valid ProdutoInput produtoInput) {

        Restaurante restaurante = restauranteService.findById(restauranteId);

        Produto produto = produtoService.findById(restauranteId, produtoId);

        produtoEntityAssembler.copyToEntity(produtoInput, produto);

        ProdutoOutput produtoOutput = produtoModelAssembler.toModel(produtoService.save(produto));

        return ResponseEntity.ok(produtoOutput);

    }

}
