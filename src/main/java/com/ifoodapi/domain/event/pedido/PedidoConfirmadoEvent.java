package com.ifoodapi.domain.event.pedido;

import com.ifoodapi.domain.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {

    private Pedido pedido;
}
