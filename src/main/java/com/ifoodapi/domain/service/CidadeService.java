package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.Cidade;
import com.ifoodapi.domain.entity.Estado;
import com.ifoodapi.domain.exception.EntidadeEmUsoException;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.NegocioException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoService estadoService;

    public CidadeService() {
    }

    public List<Cidade> findAll() {
        return cidadeRepository.findAll();
    }

    public Cidade findById(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.CIDADE_NAO_ENCONTRADA.getMensagem(), cidadeId)));
    }

    @Transactional
    public Cidade save(Cidade cidade) {

        cidade.setEstado(getEstado(cidade.getEstado().getId()));

        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void delete(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
            cidadeRepository.flush();
        } catch(DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MensagemModelException.CIDADE_EM_USO.getMensagem(), cidadeId));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MensagemModelException.CIDADE_NAO_ENCONTRADA.getMensagem(), cidadeId));
        }
    }

    private Estado getEstado(Long estadoId) {
        try {
            return estadoService.findById(estadoId);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(String.format(MensagemModelException.ESTADO_NAO_ENCONTRADO.getMensagem(), estadoId));
        }
    }
}
