package com.ifoodapi.domain.exception.model;

import lombok.Getter;

@Getter
public enum MensagemModelException {

    CIDADE_NAO_ENCONTRADA("A cidade com id %d não foi encontrada."),
    ESTADO_NAO_ENCONTRADO("O estado com id %d não foi encontrado."),
    COZINHA_NAO_ENCONTRADA("A cozinha com id %d não foi encontrada."),
    FOTO_PRODUTO_NAO_ENCONTRADA("A foto para o restauranteId %d e para o produtoId %d não foi encontrada."),
    FORMA_PAGAMENTO_NAO_ENCONTRADA("A forma de pagamento com id %d não foi encontrada."),
    GRUPO_NAO_ENCONTRADO("O grupo com id %d não foi encontrado."),
    ITEM_PEDIDO_NAO_ENCONTRADO("O item do pedido com id %d não foi encontrado."),
    PEDIDO_NAO_ENCONTRADO("O pedido com codigo %s não foi encontrado."),
    PERMISSAO_NAO_ENCONTRADA("A permissao com id %d não foi encontrada."),
    PRODUTO_NAO_ENCONTRADO("O produto com id %d para o restaurante com id %d não foi encontrado."),
    USUARIO_NAO_ENCONTRADO("O usuario com id %d não foi encontrado."),
    RESTAURANTE_NAO_ENCONTRADO("O restaurante com id %d não foi encontrado."),

    COZINHA_EM_USO("A cozinha com id %d está em uso."),
    CIDADE_EM_USO("A cidade com id %d está em uso."),
    ESTADO_EM_USO("O estado com id %d está em uso."),
    FORMA_PAGAMENTO_EM_USO("A forma de pagamento com id %d está em uso."),
    GRUPO_EM_USO("O grupo com id %d está em uso."),
    ITEM_PEDIDO_EM_USO("O item pedido com id %d está em uso."),
    PEDIDO__EM_USO("O pedido com id %d está em uso."),
    PERMISSAO_EM_USO("A permissão com id %d está em uso."),
    PRODUTO_EM_USO("O produto com id %d está em uso."),
    USUARIO_EM_USO("O usuario com id %d está em uso."),
    SENHA_INVALIDA("A senha está inválida, pense um pouco mais e quando tiver certeza coloque a senha novamente."),
    EMAIL_DUPLICADO("O email %s já existe, por favor utilize um outro e-mail."),
    RESTAURANTE_NAO_ACEITA_FORMA_PAGAMENTO("O Restaurante com id %d não tem a forma de pagamento id %d."),
    RESTAURANTE_NAO_TEM_PRODUTO("O Restaurante com id %d não tem o produto id %d."),
    PEDIDO_COM_STATUS_INVALIDO("O pedido com codigo %s não pode ser alterado do status %s para o status %s.");

    private String mensagem;

    MensagemModelException(String mensagem) {
        this.mensagem = mensagem;
    }

}
