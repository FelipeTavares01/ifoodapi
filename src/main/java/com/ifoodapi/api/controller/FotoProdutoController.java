package com.ifoodapi.api.controller;

import com.ifoodapi.api.model.input.FotoProdutoInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class FotoProdutoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateFoto(@PathVariable Long restauranteId,
                           @PathVariable Long produtoId,
                           @Valid FotoProdutoInput fotoProdutoInput) {

        var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();

        var pathArquivo = Path.of("C:/Users/felip/OneDrive/Área de Trabalho/catalogo", nomeArquivo);

        System.out.println(fotoProdutoInput.getDescricao());
        System.out.println(pathArquivo);
        System.out.println(fotoProdutoInput.getArquivo().getContentType());

        try {
            fotoProdutoInput.getArquivo().transferTo(pathArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
