package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.UsuarioModelAssembler;
import com.ifoodapi.api.model.output.UsuarioOutput;
import com.ifoodapi.domain.entity.Usuario;
import com.ifoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public ResponseEntity<List<UsuarioOutput>> findAllUsuarios(@PathVariable Long restauranteId) {
        Set<Usuario> usuarios = restauranteService.findById(restauranteId).getUsuarios();

        List<UsuarioOutput> usuariosOutputs = usuarioModelAssembler.toCollectionModel(usuarios);

        return ResponseEntity.ok(usuariosOutputs);
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkUsuarioResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.linkUsuarioResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkUsuarioResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.unlinkUsuarioResponsavel(restauranteId, usuarioId);
    }


}
