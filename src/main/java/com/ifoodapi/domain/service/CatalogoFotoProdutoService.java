package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.FotoProduto;
import com.ifoodapi.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto save(FotoProduto fotoProduto, InputStream dadosFoto) {

        Long restauranteId = fotoProduto.getProduto().getRestaurante().getId();
        Long produtoId = fotoProduto.getProduto().getId();
        String nomeArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());

        Optional<FotoProduto> fotoProdutoOptional = produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoProdutoOptional.isPresent()) {
            produtoRepository.delete(fotoProdutoOptional.get());
        }

        fotoProduto.setNomeArquivo(nomeArquivo);

        FotoProduto fotoSalva = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(dadosFoto)
                .build();

        fotoStorageService.armanezar(novaFoto);

        return fotoSalva;
    }
}
