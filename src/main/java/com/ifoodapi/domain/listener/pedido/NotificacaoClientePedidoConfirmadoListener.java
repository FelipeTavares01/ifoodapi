package com.ifoodapi.domain.listener.pedido;

import com.ifoodapi.domain.event.pedido.PedidoConfirmadoEvent;
import com.ifoodapi.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @Autowired
    private MensagemHelper mensagemHelper;

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {

        var mensagem = mensagemHelper.assemblerMensagem(event.getPedido(), "confirmado");

        envioEmailService.enviar(mensagem);
    }
}
