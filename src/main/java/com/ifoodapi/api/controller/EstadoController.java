package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.EstadoEntityAssembler;
import com.ifoodapi.api.assembler.EstadoModelAssembler;
import com.ifoodapi.api.model.input.EstadoInput;
import com.ifoodapi.api.model.output.EstadoOutput;
import com.ifoodapi.domain.entity.Estado;
import com.ifoodapi.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoEntityAssembler estadoEntityAssembler;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @GetMapping
    public ResponseEntity<List<EstadoOutput>> findAll() {
        List<EstadoOutput> estadoOutputs = estadoModelAssembler.toCollectionModel(estadoService.findAll());

        return ResponseEntity.ok(estadoOutputs);
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<EstadoOutput> findById(@PathVariable Long estadoId) {
        EstadoOutput estadoOutput = estadoModelAssembler.toModel(estadoService.findById(estadoId));

        return ResponseEntity.ok(estadoOutput);
    }

    @PostMapping
    public ResponseEntity<EstadoOutput> create(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoEntityAssembler.toEntity(estadoInput);

        EstadoOutput estadoOutput = estadoModelAssembler.toModel(estadoService.save(estado));

        return ResponseEntity.status(HttpStatus.CREATED).body(estadoOutput);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoOutput> update(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoService.findById(estadoId);

        estadoEntityAssembler.copyToEntity(estadoInput, estado);

        EstadoOutput estadoOutput = estadoModelAssembler.toModel(estadoService.save(estado));

        return ResponseEntity.ok(estadoOutput);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long estadoId) {
        estadoService.delete(estadoId);
    }
}
