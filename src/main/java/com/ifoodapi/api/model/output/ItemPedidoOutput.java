package com.ifoodapi.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemPedidoOutput {

    private Long produtoId;
    private String nomeProduto;
    private Long quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
}
