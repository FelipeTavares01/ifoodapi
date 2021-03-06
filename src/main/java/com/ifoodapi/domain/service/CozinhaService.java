package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.Cozinha;
import com.ifoodapi.domain.exception.EntidadeEmUsoException;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Page<Cozinha> findAll(Pageable pageable) {
        return cozinhaRepository.findAll(pageable);
    }

    public Cozinha findById(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.COZINHA_NAO_ENCONTRADA.getMensagem(), cozinhaId)));
    }

    @Transactional
    public Cozinha save(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void delete(Long cozinhaId) {
        try {
            cozinhaRepository.deleteById(cozinhaId);
            cozinhaRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MensagemModelException.COZINHA_NAO_ENCONTRADA.getMensagem(), cozinhaId));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MensagemModelException.COZINHA_EM_USO.getMensagem(), cozinhaId));
        }
    }
}
