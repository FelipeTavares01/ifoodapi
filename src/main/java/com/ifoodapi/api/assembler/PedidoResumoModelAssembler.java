package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.PedidoResumoOutput;
import com.ifoodapi.domain.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoOutput toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoOutput.class);
    }

    public List<PedidoResumoOutput> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
}
