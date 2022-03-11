package com.ifoodapi.domain.listener.pedido;

import com.ifoodapi.domain.entity.Pedido;
import com.ifoodapi.domain.service.EnvioEmailService;
import org.springframework.stereotype.Component;

@Component
public class MensagemHelper {

    public EnvioEmailService.Mensagem assemblerMensagem(Pedido pedido, String statusPedido) {
        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido " + statusPedido)
                .destinatario(pedido.getCliente().getEmail())
                .variavel("pedido", pedido)
                .corpo("pedido-" + statusPedido + ".html")
                .build();
        return mensagem;
    }
}
