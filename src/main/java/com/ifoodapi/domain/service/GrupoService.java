package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.Grupo;
import com.ifoodapi.domain.entity.Permissao;
import com.ifoodapi.domain.exception.EntidadeEmUsoException;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private PermissaoService permissaoService;

    public List<Grupo> findAll() {
        return grupoRepository.findAll();
    }

    public Grupo findById(Long grupoId) {
        return grupoRepository.findById(grupoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.GRUPO_NAO_ENCONTRADO.getMensagem(), grupoId)));
    }

    @Transactional
    public Grupo save(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void delete(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MensagemModelException.GRUPO_NAO_ENCONTRADO.getMensagem(), grupoId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MensagemModelException.GRUPO_EM_USO.getMensagem(), grupoId));
        }
    }

    @Transactional
    public void linkPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = this.findById(grupoId);

        Permissao permissao = permissaoService.findById(permissaoId);

        grupo.linkPermissao(permissao);
    }

    @Transactional
    public void unlinkPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = this.findById(grupoId);

        Permissao permissao = permissaoService.findById(permissaoId);

        grupo.unlinkPermissao(permissao);
    }
}
