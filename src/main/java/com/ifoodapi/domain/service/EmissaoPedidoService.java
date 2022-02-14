package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.*;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.NegocioException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private ProdutoService produtoService;

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validaPedido(pedido);

        pedido.calculaValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validaPedido(Pedido pedido) {
        try {
            Restaurante restaurante = restauranteService.findById(pedido.getRestaurante().getId());
            Cidade cidade = cidadeService.findById(pedido.getEnderecoEntrega().getCidade().getId());
            FormaPagamento formaPagamento = formaPagamentoService.findById(pedido.getFormaPagamento().getId());
            Usuario cliente = usuarioService.findById(7L);
            validaRestauranteAceitaFormaPagamento(restaurante, formaPagamento);
            validaItens(pedido);

            pedido.setCliente(cliente);
            pedido.getEnderecoEntrega().setCidade(cidade);
            pedido.setFormaPagamento(formaPagamento);
            pedido.setRestaurante(restaurante);
            pedido.setTaxaFrete(restaurante.getTaxaFrete());
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private void validaRestauranteAceitaFormaPagamento(Restaurante restaurante, FormaPagamento formaPagamento) {
        if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(
                    String.format(MensagemModelException.RESTAURANTE_NAO_ACEITA_FORMA_PAGAMENTO.getMensagem(),
                            restaurante.getId(), formaPagamento.getId()));
        }
    }

    private void validaItens(Pedido pedido) {
        pedido.getItens().stream().forEach(item -> {
            Long restauranteId = pedido.getRestaurante().getId();
            Long produtoId = item.getProduto().getId();

            try{
                Produto produto = produtoService.findById(restauranteId, produtoId);

                item.setProduto(produto);
                item.setPedido(pedido);
                item.setPrecoUnitario(produto.getPreco());
            } catch (EntidadeNaoEncontradaException e) {
                throw new NegocioException(
                        String.format(MensagemModelException.RESTAURANTE_NAO_TEM_PRODUTO.getMensagem(), restauranteId, produtoId));
            }
        });

    }

}
