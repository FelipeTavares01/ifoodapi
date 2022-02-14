package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.PermissaoModelAssembler;
import com.ifoodapi.api.model.output.PermissaoOutput;
import com.ifoodapi.domain.entity.Grupo;
import com.ifoodapi.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @GetMapping
    public ResponseEntity<List<PermissaoOutput>> findAllPermissoes(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.findById(grupoId);

        List<PermissaoOutput> permissoesOutputs = permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());

        return ResponseEntity.ok(permissoesOutputs);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.linkPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.unlinkPermissao(grupoId, permissaoId);
    }
}
