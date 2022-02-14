package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.CidadeEntityAssembler;
import com.ifoodapi.api.assembler.CidadeModelAssembler;
import com.ifoodapi.api.model.input.CidadeInput;
import com.ifoodapi.api.model.output.CidadeOutput;
import com.ifoodapi.domain.entity.Cidade;
import com.ifoodapi.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeEntityAssembler cidadeEntityAssembler;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @GetMapping
    public ResponseEntity<List<CidadeOutput>> findAll() {
        List<CidadeOutput> cidadeOutputs =cidadeModelAssembler.toCollectionModel(cidadeService.findAll());

        return ResponseEntity.ok(cidadeOutputs);
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeOutput> findById(@PathVariable Long cidadeId) {
        CidadeOutput cidadeOutput = cidadeModelAssembler.toModel(cidadeService.findById(cidadeId));

        return ResponseEntity.ok(cidadeOutput);
    }

    @PostMapping
    public ResponseEntity<CidadeOutput> create(@RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidade = cidadeEntityAssembler.toEntity(cidadeInput);

        CidadeOutput cidadeOutput = cidadeModelAssembler.toModel(cidadeService.save(cidade));

        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeOutput);
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeOutput> update(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidade = cidadeService.findById(cidadeId);

        cidadeEntityAssembler.copyToEntity(cidadeInput, cidade);

        CidadeOutput cidadeOutput = cidadeModelAssembler.toModel(cidadeService.save(cidade));

        return ResponseEntity.ok(cidadeOutput);
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cidadeId) {
        cidadeService.delete(cidadeId);
    }

}
