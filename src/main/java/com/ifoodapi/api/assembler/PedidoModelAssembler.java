package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.PedidoOutput;
import com.ifoodapi.domain.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoOutput toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoOutput.class);
    }

    public List<PedidoOutput> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
}
