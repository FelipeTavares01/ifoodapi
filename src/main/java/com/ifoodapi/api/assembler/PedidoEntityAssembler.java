package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.input.PedidoInput;
import com.ifoodapi.domain.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoEntityAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toEntity(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }
}
