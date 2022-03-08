package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.FotoProdutoEntityAssembler;
import com.ifoodapi.api.assembler.FotoProdutoModelAssembler;
import com.ifoodapi.api.model.input.FotoProdutoInput;
import com.ifoodapi.api.model.output.FotoProdutoOutput;
import com.ifoodapi.domain.entity.FotoProduto;
import com.ifoodapi.domain.entity.Produto;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.NegocioException;
import com.ifoodapi.domain.repository.ProdutoRepository;
import com.ifoodapi.domain.repository.RestauranteRepository;
import com.ifoodapi.domain.service.CatalogoFotoProdutoService;
import com.ifoodapi.domain.service.ProdutoService;
import com.ifoodapi.domain.service.RestauranteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FotoProdutoOutput> findById(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = catalogoFotoProdutoService.findById(restauranteId, produtoId);
        FotoProdutoOutput fotoProdutoOutput = fotoProdutoModelAssembler.toModel(fotoProduto);

        return ResponseEntity.ok(fotoProdutoOutput);
    }


    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<InputStreamResource> getFotoProduto(@PathVariable Long restauranteId,
                                                              @PathVariable Long produtoId,
                                                              @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.findById(restauranteId, produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            InputStream inputStream = catalogoFotoProdutoService.getFotoProduto(fotoProduto.getNomeArquivo());

            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoOutput> updateFoto(@PathVariable Long restauranteId,
                                                        @PathVariable Long produtoId,
                                                        @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        Produto produto = produtoService.findById(restauranteId, produtoId);

        FotoProduto fotoProduto = produtoEntityAssembler.toEntity(fotoProdutoInput, produto);

        fotoProduto = catalogoFotoProdutoService.save(fotoProduto, fotoProdutoInput.getArquivo().getInputStream());

        return ResponseEntity.ok(fotoProdutoModelAssembler.toModel(fotoProduto));

    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto,
                                                   List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {

        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if(!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }

    }
}
