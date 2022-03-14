package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.FormaPagamentoEntityAssembler;
import com.ifoodapi.api.assembler.FormaPagamentoModelAssembler;
import com.ifoodapi.api.model.input.FormaPagamentoInput;
import com.ifoodapi.api.model.output.FormaPagamentoOutput;
import com.ifoodapi.domain.entity.FormaPagamento;
import com.ifoodapi.domain.repository.FormaPagamentoRepository;
import com.ifoodapi.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<List<FormaPagamentoOutput>> findAll(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = getETag();

        if(request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamentoOutput> formasPagamentoOutputs = formaPagamentoModelAssembler
                .toCollectionModel(formaPagamentoService.findAll());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formasPagamentoOutputs);
    }

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoOutput> findById(@PathVariable Long formaPagamentoId,
                                                         ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = getETag();

        if(request.checkNotModified(eTag)) {
            return null;
        }

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
    public ResponseEntity<FormaPagamentoOutput> update(@PathVariable Long formaPagamentoId,
                                                       @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
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

    private String getETag() {
        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

        if(Objects.nonNull(dataUltimaAtualizacao)) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }
        return eTag;
    }
}
