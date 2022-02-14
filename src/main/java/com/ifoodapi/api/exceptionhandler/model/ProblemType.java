package com.ifoodapi.api.exceptionhandler.model;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("Recurso não encontrado."),
    ENTIDADE_EM_USO("Entidade em uso."),
    VIOLACAO_REGRA_NEGOCIO("Violação regra de negocio."),
    JSON_INVALIDO("Json inválido."),
    PARAMETRO_URL_INVALIDO("Parametro de url inválido."),
    ERRO_INTERNO("Erro interno."),
    DADOS_INVALIDOS("Dados inválidos.");

    private String title;

    ProblemType(String title) {
        this.title = title;
    }
}
