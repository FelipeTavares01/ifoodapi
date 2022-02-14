package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.FormaPagamentoEntityAssembler;
import com.ifoodapi.api.assembler.FormaPagamentoModelAssembler;
import com.ifoodapi.api.model.input.FormaPagamentoInput;
import com.ifoodapi.api.model.output.FormaPagamentoOutput;
import com.ifoodapi.domain.entity.FormaPagamento;
import com.ifoodapi.domain.repository.FormaPagamentoRepository;
import com.ifoodapi.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoEntityAssembler formaPagamentoEntityAssembler;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;


    @GetMapping
    public ResponseEntity<List<FormaPagamentoOutput>> findAll() {
        List<FormaPagamentoOutput> formasPagamentoOutputs = formaPagamentoModelAssembler
                .toCollectionModel(formaPagamentoService.findAll());

        return ResponseEntity.ok(formasPagamentoOutputs);
    }

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoOutput> findById(@PathVariable Long formaPagamentoId) {
        FormaPagamentoOutput formaPagamentoOutput = formaPagamentoModelAssembler
                .toModel(formaPagamentoService.findById(formaPagamentoId));

        return ResponseEntity.ok(formaPagamentoOutput);
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoOutput> create(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoEntityAssembler.toEntity(formaPagamentoInput);
        FormaPagamentoOutput formaPagamentoOutput = formaPagamentoModelAssembler
                .toModel(formaPagamentoService.save(formaPagamento));

        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoOutput);
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoOutput> update(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoService.findById(formaPagamentoId);

        formaPagamentoEntityAssembler.copyToEntity(formaPagamentoInput, formaPagamento);

        FormaPagamentoOutput formaPagamentoOutput = formaPagamentoModelAssembler
                .toModel(formaPagamentoService.save(formaPagamento));

        return ResponseEntity.ok(formaPagamentoOutput);

    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.delete(formaPagamentoId);
    }
}
