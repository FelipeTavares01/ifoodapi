package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.Permissao;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao findById(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.PERMISSAO_NAO_ENCONTRADA.getMensagem(), permissaoId)));
    }
}
