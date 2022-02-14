package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.FormaPagamentoModelAssembler;
import com.ifoodapi.api.model.output.FormaPagamentoOutput;
import com.ifoodapi.domain.entity.Restaurante;
import com.ifoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoOutput>> findAll(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);

        List<FormaPagamentoOutput> formaPagamentoOutputs = formaPagamentoModelAssembler
                .toCollectionModel(restaurante.getFormasPagamento());

        return ResponseEntity.ok(formaPagamentoOutputs);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.linkFormaPagamento(restauranteId, formaPagamentoId);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.unlinkFormaPagamento(restauranteId, formaPagamentoId);
    }
}
