package com.ifoodapi.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoOutput {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
}
