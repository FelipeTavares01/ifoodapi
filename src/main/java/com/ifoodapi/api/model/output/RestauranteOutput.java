package com.ifoodapi.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteOutput {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaOutput cozinha;
    private EnderecoOutput endereco;
    private Boolean ativo;
    private Boolean aberto;

}
