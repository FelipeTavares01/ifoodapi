package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.GrupoModelAssembler;
import com.ifoodapi.api.model.output.GrupoOutput;
import com.ifoodapi.domain.entity.Grupo;
import com.ifoodapi.domain.entity.Usuario;
import com.ifoodapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @GetMapping
    public ResponseEntity<List<GrupoOutput>> findAllGrupos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.findById(usuarioId);

        List<Grupo> gruposOrdenados = usuario.getGrupos().stream().sorted().collect(Collectors.toList());

        List<GrupoOutput> gruposOutputs = grupoModelAssembler.toCollectionModel(gruposOrdenados);

        return ResponseEntity.ok(gruposOutputs);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.linkGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.unlinkGrupo(usuarioId, grupoId);
    }
}
