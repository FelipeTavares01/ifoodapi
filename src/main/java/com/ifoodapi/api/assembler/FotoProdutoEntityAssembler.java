package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.input.FotoProdutoInput;
import com.ifoodapi.domain.entity.FotoProduto;
import com.ifoodapi.domain.entity.Produto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FotoProdutoEntityAssembler {

    public FotoProduto toEntity(FotoProdutoInput fotoProdutoInput, Produto produto) {
        FotoProduto fotoProduto = new FotoProduto();
        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        fotoProduto.setProduto(produto);
        fotoProduto.setId(produto.getId());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        return fotoProduto;
    }
}
