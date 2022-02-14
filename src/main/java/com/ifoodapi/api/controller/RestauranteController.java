package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.RestauranteEntityAssembler;
import com.ifoodapi.api.assembler.RestauranteModelAssembler;
import com.ifoodapi.api.model.input.RestauranteInput;
import com.ifoodapi.api.model.output.RestauranteOutput;
import com.ifoodapi.domain.entity.Restaurante;
import com.ifoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteEntityAssembler restauranteEntityAssembler;

    @GetMapping
    public ResponseEntity<List<RestauranteOutput>> findAll() {

        List<RestauranteOutput> restauranteOutputs = restauranteModelAssembler.toCollectionModel(restauranteService.findAll());

        return ResponseEntity.ok(restauranteOutputs);
    }

    @GetMapping("/por-nome-e-frete")
    public ResponseEntity<List<RestauranteOutput>> findAllByFilter(@RequestParam(required = false) String nome,
                                                             @RequestParam(required = false) BigDecimal taxaFreteInicial,
                                                             @RequestParam(required = false) BigDecimal taxaFreteFinal) {

        List<Restaurante> restaurantes = restauranteService.findAllByFilter(nome, taxaFreteInicial, taxaFreteFinal);

        List<RestauranteOutput> restauranteOutputs = restauranteModelAssembler.toCollectionModel(restaurantes);

        return ResponseEntity.ok(restauranteOutputs);
    }

    @GetMapping("/com-frete-gratis")
    public ResponseEntity<List<RestauranteOutput>> findAllByFreteGratis(@RequestParam(required = false) String nome) {

        List<Restaurante> restaurantes = restauranteService.findAllByFreteGratis(nome);

        List<RestauranteOutput> restauranteOutputs = restauranteModelAssembler.toCollectionModel(restaurantes);

        return ResponseEntity.ok(restauranteOutputs);
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteOutput> findById(@PathVariable Long restauranteId) {
        RestauranteOutput restauranteOutput = restauranteModelAssembler.toModel(restauranteService.findById(restauranteId));

        return ResponseEntity.ok(restauranteOutput);
    }

    @PostMapping
    public ResponseEntity<RestauranteOutput> create(@RequestBody @Valid RestauranteInput restauranteInput) {

        Restaurante restaurante = restauranteEntityAssembler.toEntity(restauranteInput);

        RestauranteOutput restauranteOutput = restauranteModelAssembler.toModel(restauranteService.save(restaurante));

        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteOutput);
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<RestauranteOutput> update(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {

        Restaurante restaurante = restauranteService.findById(restauranteId);

        restauranteEntityAssembler.copyToEntity(restauranteInput, restaurante);

        RestauranteOutput restauranteOutput = restauranteModelAssembler.toModel(restauranteService.save(restaurante));

        return ResponseEntity.ok(restauranteOutput);
    }

    @PutMapping("/{restauranteId}/ativo")
    public ResponseEntity<RestauranteOutput> active(@PathVariable Long restauranteId) {
        RestauranteOutput restauranteOutput = restauranteModelAssembler.toModel(restauranteService.active(restauranteId));

        return ResponseEntity.ok(restauranteOutput);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activeBatch(@RequestBody List<Long> restauranteIds) {
        restauranteService.activeBatch(restauranteIds);
    }

    @PutMapping("/{restauranteId}/inativo")
    public ResponseEntity<RestauranteOutput> inactive(@PathVariable Long restauranteId) {
        RestauranteOutput restauranteOutput = restauranteModelAssembler.toModel(restauranteService.inactive(restauranteId));

        return ResponseEntity.ok(restauranteOutput);
    }

    @PutMapping("/inativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactiveBatch(@RequestBody List<Long> restauranteIds) {
        restauranteService.inactiveBatch(restauranteIds);
    }

    @PutMapping("/{restauranteId}/abertura")
    public ResponseEntity<RestauranteOutput> opening(@PathVariable Long restauranteId) {
        RestauranteOutput restauranteOutput = restauranteModelAssembler.toModel(restauranteService.opening(restauranteId));

        return ResponseEntity.ok(restauranteOutput);
    }

    @PutMapping("/{restauranteId}/fechamento")
    public ResponseEntity<RestauranteOutput> closure(@PathVariable Long restauranteId) {
        RestauranteOutput restauranteOutput = restauranteModelAssembler.toModel(restauranteService.closure(restauranteId));

        return  ResponseEntity.ok(restauranteOutput);
    }
}
