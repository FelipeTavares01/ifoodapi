package com.ifoodapi.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeOutput {

    private Long id;
    private String nome;
    private EstadoOutput estado;
}
