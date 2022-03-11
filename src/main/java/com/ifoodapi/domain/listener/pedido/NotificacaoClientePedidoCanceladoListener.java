package com.ifoodapi.domain.listener.pedido;

import com.ifoodapi.domain.event.pedido.PedidoCanceladoEvent;
import com.ifoodapi.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @Autowired
    private MensagemHelper mensagemHelper;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {

        var mensagem = mensagemHelper.assemblerMensagem(event.getPedido(), "cancelado");

        envioEmailService.enviar(mensagem);
    }
}
