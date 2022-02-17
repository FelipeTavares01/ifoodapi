package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.FormaPagamento;
import com.ifoodapi.domain.exception.EntidadeEmUsoException;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> findAll() {
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento findById(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.FORMA_PAGAMENTO_NAO_ENCONTRADA.getMensagem(), formaPagamentoId)));
    }

    @Transactional
    public FormaPagamento save(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void delete(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
            formaPagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MensagemModelException.FORMA_PAGAMENTO_NAO_ENCONTRADA.getMensagem(), formaPagamentoId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MensagemModelException.FORMA_PAGAMENTO_EM_USO.getMensagem(), formaPagamentoId));
        }
    }
}
