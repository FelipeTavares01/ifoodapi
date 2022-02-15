package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.Pedido;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.PedidoRepository;
import com.ifoodapi.domain.repository.filter.PedidoFilter;
import com.ifoodapi.infrastructure.repository.specs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.ifoodapi.infrastructure.repository.specs.PedidoSpecs.*;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Page<Pedido> findAllByFilter(PedidoFilter pedidoFilter, Pageable pageable) {
        return pedidoRepository.findAll(comFiltros(pedidoFilter), pageable);
    }

    public Pedido findByCodigo(String pedidoCodigo) {
        return pedidoRepository.findByCodigo(pedidoCodigo)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.PEDIDO_NAO_ENCONTRADO.getMensagem(), pedidoCodigo)));
    }

    @Transactional
    public void confirm(String pedidoCodigo) {
        Pedido pedido = this.findByCodigo(pedidoCodigo);
        pedido.confirmar();
    }

    @Transactional
    public void delivered(String pedidoCodigo) {
        Pedido pedido = this.findByCodigo(pedidoCodigo);
        pedido.entregar();
    }

    @Transactional
    public void cancel(String pedidoCodigo) {
        Pedido pedido = this.findByCodigo(pedidoCodigo);
        pedido.cancelar();
    }
}
