package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.UsuarioEntityAssembler;
import com.ifoodapi.api.assembler.UsuarioModelAssembler;
import com.ifoodapi.api.model.input.SenhaInput;
import com.ifoodapi.api.model.input.UsuarioInput;
import com.ifoodapi.api.model.input.UsuarioSemSenhaInput;
import com.ifoodapi.api.model.output.UsuarioOutput;
import com.ifoodapi.domain.entity.Usuario;
import com.ifoodapi.domain.repository.UsuarioRepository;
import com.ifoodapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioEntityAssembler usuarioEntityAssembler;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public ResponseEntity<List<UsuarioOutput>> findAll() {
        List<UsuarioOutput> usuariosOutputs = usuarioModelAssembler.toCollectionModel(usuarioService.findAll());

        return ResponseEntity.ok(usuariosOutputs);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioOutput> findById(@PathVariable Long usuarioId) {
        UsuarioOutput usuarioOutput = usuarioModelAssembler.toModel(usuarioService.findById(usuarioId));

        return ResponseEntity.ok(usuarioOutput);
    }

    @PostMapping
    public ResponseEntity<UsuarioOutput> create(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = usuarioEntityAssembler.toEntity(usuarioInput);

        UsuarioOutput usuarioOutput = usuarioModelAssembler.toModel(usuarioService.save(usuario));

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOutput);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioOutput> update(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioSemSenhaInput usuarioSemSenhaInput) {
        Usuario usuario = usuarioService.findById(usuarioId);

        usuarioEntityAssembler.copyToEntity(usuarioSemSenhaInput, usuario);

        UsuarioOutput usuarioOutput = usuarioModelAssembler.toModel(usuarioService.save(usuario));

        return ResponseEntity.ok(usuarioOutput);

    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {
        usuarioService.changePassword(usuarioId, senhaInput);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long usuarioId) {
        usuarioService.delete(usuarioId);
    }



}
