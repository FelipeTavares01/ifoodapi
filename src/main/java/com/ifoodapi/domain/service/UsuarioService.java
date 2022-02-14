package com.ifoodapi.domain.service;

import com.ifoodapi.api.model.input.SenhaInput;
import com.ifoodapi.domain.entity.Grupo;
import com.ifoodapi.domain.entity.Usuario;
import com.ifoodapi.domain.exception.EntidadeEmUsoException;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.NegocioException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
import com.ifoodapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoService grupoService;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.USUARIO_NAO_ENCONTRADO.getMensagem(), usuarioId)));
    }

    @Transactional
    public Usuario save(Usuario usuario) {

        usuarioRepository.detach(usuario);

        validaEmailExistente(usuario);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void changePassword(Long usuarioId, SenhaInput senhaInput) {
        Usuario usuario = this.findById(usuarioId);

        validaSenha(usuario.getSenha(), senhaInput.getSenhaAtual());

        usuario.setSenha(senhaInput.getNovaSenha());
    }

    @Transactional
    public void delete(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MensagemModelException.USUARIO_NAO_ENCONTRADO.getMensagem(), usuarioId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MensagemModelException.USUARIO_EM_USO.getMensagem(), usuarioId));
        }
    }

    @Transactional
    public void linkGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = this.findById(usuarioId);

        Grupo grupo = grupoService.findById(grupoId);

        usuario.linkGrupo(grupo);
    }

    @Transactional
    public void unlinkGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = this.findById(usuarioId);

        Grupo grupo = grupoService.findById(grupoId);

        usuario.unlinkGrupo(grupo);
    }

    private void validaSenha(String senhaUsuario, String senhaAtual) {
        if(senhaInvalida(senhaUsuario, senhaAtual)) {
            throw new NegocioException(String.format(MensagemModelException.SENHA_INVALIDA.getMensagem()));
        }
    }

    private boolean senhaInvalida(String senhaUsuario, String senhaAtual) {
        return !senhaUsuario.equalsIgnoreCase(senhaAtual);
    }

    private void validaEmailExistente(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (emailJaExiste(usuario, usuarioExistente)) {
            throw new NegocioException(String.format(MensagemModelException.EMAIL_DUPLICADO.getMensagem(), usuario.getEmail()));
        }
    }

    private boolean emailJaExiste(Usuario usuario, Optional<Usuario> usuarioExistente) {
        return usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario);
    }

}
