package com.ifoodapi.api.controller;

import com.ifoodapi.api.assembler.FotoProdutoEntityAssembler;
import com.ifoodapi.api.assembler.FotoProdutoModelAssembler;
import com.ifoodapi.api.model.input.FotoProdutoInput;
import com.ifoodapi.api.model.output.FotoProdutoOutput;
import com.ifoodapi.domain.entity.FotoProduto;
import com.ifoodapi.domain.entity.Produto;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.service.CatalogoFotoProdutoService;
import com.ifoodapi.domain.service.FotoStorageService;
import com.ifoodapi.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<?> getFotoProduto(@PathVariable Long restauranteId,
                                                              @PathVariable Long produtoId,
                                                              @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.findById(restauranteId, produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            FotoStorageService.FotoRecuperada fotoRecuperada = catalogoFotoProdutoService.getFotoProduto(fotoProduto.getNomeArquivo());

            if(fotoRecuperada.temUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl().toString())
                        .build();
            }

            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));

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

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProdutoService.delete(restauranteId, produtoId);
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
