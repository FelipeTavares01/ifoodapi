package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.FotoProdutoEntityAssembler;
import com.ifoodapi.api.assembler.FotoProdutoModelAssembler;
import com.ifoodapi.api.model.input.FotoProdutoInput;
import com.ifoodapi.api.model.output.FotoProdutoOutput;
import com.ifoodapi.domain.entity.FotoProduto;
import com.ifoodapi.domain.entity.Produto;
import com.ifoodapi.domain.repository.ProdutoRepository;
import com.ifoodapi.domain.repository.RestauranteRepository;
import com.ifoodapi.domain.service.CatalogoFotoProdutoService;
import com.ifoodapi.domain.service.ProdutoService;
import com.ifoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class FotoProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @Autowired
    private FotoProdutoEntityAssembler produtoEntityAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoOutput> updateFoto(@PathVariable Long restauranteId,
                                                        @PathVariable Long produtoId,
                                                        @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        Produto produto = produtoService.findById(restauranteId, produtoId);

        FotoProduto fotoProduto = produtoEntityAssembler.toEntity(fotoProdutoInput, produto);

        fotoProduto = catalogoFotoProdutoService.save(fotoProduto, fotoProdutoInput.getArquivo().getInputStream());

        return ResponseEntity.ok(fotoProdutoModelAssembler.toModel(fotoProduto));

    }

}
