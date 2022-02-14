package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.Estado;
import com.ifoodapi.domain.exception.EntidadeEmUsoException;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    public Estado findById(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.ESTADO_NAO_ENCONTRADO.getMensagem(), estadoId)));
    }

    @Transactional
    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional
    public void delete(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
            estadoRepository.flush();
        } catch(DataIntegrityViolationException e) {
           throw new EntidadeEmUsoException(String.format(MensagemModelException.ESTADO_EM_USO.getMensagem(), estadoId));
        } catch(EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MensagemModelException.ESTADO_NAO_ENCONTRADO.getMensagem(), estadoId));
        }
    }
}
