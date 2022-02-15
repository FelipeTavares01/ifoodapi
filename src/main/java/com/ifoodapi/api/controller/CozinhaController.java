package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.CozinhaEntityAssembler;
import com.ifoodapi.api.assembler.CozinhaModelAssembler;
import com.ifoodapi.api.model.input.CozinhaInput;
import com.ifoodapi.api.model.output.CozinhaOutput;
import com.ifoodapi.domain.entity.Cozinha;
import com.ifoodapi.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaEntityAssembler cozinhaEntityAssembler;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @GetMapping
    public ResponseEntity<Page<CozinhaOutput>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaService.findAll(pageable);

        List<CozinhaOutput> cozinhaOutputs = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());

        Page<CozinhaOutput> cozinhaOutputPage = new PageImpl<CozinhaOutput>(cozinhaOutputs, pageable, cozinhasPage.getTotalElements());

        return ResponseEntity.ok(cozinhaOutputPage);
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaOutput> findById(@PathVariable Long cozinhaId) {

        CozinhaOutput cozinhaOutput = cozinhaModelAssembler.toModel(cozinhaService.findById(cozinhaId));

        return ResponseEntity.ok(cozinhaOutput);
    }

    @PostMapping
    public ResponseEntity<CozinhaOutput> create(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaEntityAssembler.toEntity(cozinhaInput);

        CozinhaOutput cozinhaOutput = cozinhaModelAssembler.toModel(cozinhaService.save(cozinha));

        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaOutput);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaOutput> update(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {

        Cozinha cozinha = cozinhaService.findById(cozinhaId);

        cozinhaEntityAssembler.copyToEntity(cozinhaInput, cozinha);

        CozinhaOutput cozinhaOutput = cozinhaModelAssembler.toModel(cozinhaService.save(cozinha));

        return ResponseEntity.ok(cozinhaOutput);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cozinhaId) {
        cozinhaService.delete(cozinhaId);
    }
}
