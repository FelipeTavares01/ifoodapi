package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.GrupoEntityAssembler;
import com.ifoodapi.api.assembler.GrupoModelAssembler;
import com.ifoodapi.api.model.input.GrupoInput;
import com.ifoodapi.api.model.output.GrupoOutput;
import com.ifoodapi.domain.entity.Grupo;
import com.ifoodapi.domain.repository.GrupoRepository;
import com.ifoodapi.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoEntityAssembler grupoEntityAssembler;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @GetMapping
    public ResponseEntity<List<GrupoOutput>> findAll() {

        List<GrupoOutput> grupos = grupoModelAssembler.toCollectionModel(grupoService.findAll());

        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/{grupoId}")
    public ResponseEntity<GrupoOutput> findById(@PathVariable Long grupoId) {
        GrupoOutput grupoOutput = grupoModelAssembler.toModel(grupoService.findById(grupoId));

        return ResponseEntity.ok(grupoOutput);
    }

    @PostMapping
    public ResponseEntity<GrupoOutput> create(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoEntityAssembler.toEntity(grupoInput);

        GrupoOutput grupoOutput = grupoModelAssembler.toModel(grupoService.save(grupo));

        return ResponseEntity.status(HttpStatus.CREATED).body(grupoOutput);
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoOutput> update(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoService.findById(grupoId);

        grupoEntityAssembler.copyToEntity(grupoInput, grupo);

        GrupoOutput grupoOutput = grupoModelAssembler.toModel(grupoService.save(grupo));

        return ResponseEntity.ok(grupoOutput);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long grupoId) {
        grupoService.delete(grupoId);
    }
}
