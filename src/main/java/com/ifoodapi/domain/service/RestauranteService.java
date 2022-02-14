package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.*;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.NegocioException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private UsuarioService usuarioService;

    public List<Restaurante> findAll() {
        return restauranteRepository.findAll();
    }

    public List<Restaurante> findAllByFilter(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return restauranteRepository.findAllByFilter(nome, taxaFreteInicial, taxaFreteFinal);
    }

    public List<Restaurante> findAllByFreteGratis(String nome) {
        return restauranteRepository.findAllByFreteGratis(nome);
    }

    public Restaurante findById(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.RESTAURANTE_NAO_ENCONTRADO.getMensagem(), restauranteId)));
    }

    @Transactional
    public Restaurante save(Restaurante restaurante) {

        Cozinha cozinha = getCozinha(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);

        Cidade cidade = getCidade(restaurante.getEndereco().getCidade().getId());
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public Restaurante active(Long restauranteId) {
        Restaurante restaurante = this.findById(restauranteId);

        restaurante.active();

        return restaurante;
    }

    @Transactional
    public void activeBatch(List<Long> restauranteIds) {
        try {
            restauranteIds.stream().forEach(this::active);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    public Restaurante inactive(Long restauranteId) {
        Restaurante restaurante = this.findById(restauranteId);

        restaurante.inactive();

        return restaurante;
    }

    @Transactional
    public void inactiveBatch(List<Long> restauranteIds) {
        try{
            restauranteIds.stream().forEach(this::inactive);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    public void linkFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = this.findById(restauranteId);

        FormaPagamento formaPagamento = getFormaPagamento(formaPagamentoId);

        restaurante.linkFormaPagamento(formaPagamento);
    }

    @Transactional
    public void unlinkFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = this.findById(restauranteId);

        FormaPagamento formaPagamento = getFormaPagamento(formaPagamentoId);

        restaurante.unlinkFormaPagamento(formaPagamento);
    }

    @Transactional
    public void linkUsuarioResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = this.findById(restauranteId);

        Usuario usuario = usuarioService.findById(usuarioId);

        restaurante.linkUsuarioResponsavel(usuario);
    }

    @Transactional
    public void unlinkUsuarioResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = this.findById(restauranteId);

        Usuario usuario = usuarioService.findById(usuarioId);

        restaurante.unlinkUsuarioResponsavel(usuario);
    }

    @Transactional
    public Restaurante opening(Long restauranteId) {
        Restaurante restaurante = this.findById(restauranteId);

        restaurante.opening();

        return restaurante;
    }

    @Transactional
    public Restaurante closure(Long restauranteId) {
        Restaurante restaurante = this.findById(restauranteId);

        restaurante.closure();

        return restaurante;
    }

    private Cozinha getCozinha(Long cozinhaId) {
        try {
            return cozinhaService.findById(cozinhaId);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(String.format(MensagemModelException.COZINHA_NAO_ENCONTRADA.getMensagem(), cozinhaId));
        }
    }

    private Cidade getCidade(Long cidadeId) {
        try {
            return cidadeService.findById(cidadeId);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(String.format(MensagemModelException.CIDADE_NAO_ENCONTRADA.getMensagem(), cidadeId));
        }
    }

    private FormaPagamento getFormaPagamento(Long formaPagamentoId) {
        return formaPagamentoService.findById(formaPagamentoId);
    }

}
