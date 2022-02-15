package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.PedidoEntityAssembler;
import com.ifoodapi.api.assembler.PedidoModelAssembler;
import com.ifoodapi.api.assembler.PedidoResumoModelAssembler;
import com.ifoodapi.api.model.input.PedidoInput;
import com.ifoodapi.api.model.output.PedidoOutput;
import com.ifoodapi.api.model.output.PedidoResumoOutput;
import com.ifoodapi.domain.entity.Pedido;
import com.ifoodapi.domain.repository.filter.PedidoFilter;
import com.ifoodapi.domain.service.EmissaoPedidoService;
import com.ifoodapi.domain.service.PedidoService;
import com.ifoodapi.infrastructure.repository.specs.*;
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
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoEntityAssembler pedidoEntityAssembler;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Page<PedidoResumoOutput>> findAllByFilter(PedidoFilter pedidoFilter, @PageableDefault(size = 5) Pageable pageable) {

        Page<Pedido> pedidosPage = pedidoService.findAllByFilter(pedidoFilter, pageable);

        List<PedidoResumoOutput> pedidosResumoOutputs = pedidoResumoModelAssembler.toCollectionModel(pedidosPage.getContent());

        Page<PedidoResumoOutput> pedidoResumoOutputPage = new PageImpl<>(pedidosResumoOutputs, pageable, pedidosPage.getTotalElements());

        return ResponseEntity.ok(pedidoResumoOutputPage);
    }

    @GetMapping("/{pedidoCodigo}")
    public ResponseEntity<PedidoOutput> findById(@PathVariable String pedidoCodigo) {
        PedidoOutput pedidoOutput = pedidoModelAssembler.toModel(pedidoService.findByCodigo(pedidoCodigo));

        return ResponseEntity.ok(pedidoOutput);
    }

    @PostMapping
    public ResponseEntity<PedidoOutput> create(@RequestBody @Valid PedidoInput pedidoInput) {
        Pedido pedido = pedidoEntityAssembler.toEntity(pedidoInput);

        PedidoOutput pedidoOutput = pedidoModelAssembler.toModel(emissaoPedidoService.emitir(pedido));

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoOutput);
    }

    @PutMapping("/{pedidoCodigo}/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable String pedidoCodigo) {
        pedidoService.confirm(pedidoCodigo);
    }

    @PutMapping("/{pedidoCodigo}/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delivered(@PathVariable String pedidoCodigo) {
        pedidoService.delivered(pedidoCodigo);
    }

    @PutMapping("/{pedidoCodigo}/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable String pedidoCodigo) {
        pedidoService.cancel(pedidoCodigo);
    }

}
