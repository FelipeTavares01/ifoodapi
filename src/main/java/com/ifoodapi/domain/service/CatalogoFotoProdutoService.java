package com.ifoodapi.domain.service;

import com.ifoodapi.domain.entity.FotoProduto;
import com.ifoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.ifoodapi.domain.exception.model.MensagemModelException;
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

    public FotoProduto findById(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MensagemModelException.FOTO_PRODUTO_NAO_ENCONTRADA.getMensagem(), restauranteId, produtoId)));
    }

    public FotoStorageService.FotoRecuperada getFotoProduto(String nomeArquivo) {
        return fotoStorageService.recuperar(nomeArquivo);
    }

    @Transactional
    public FotoProduto save(FotoProduto fotoProduto, InputStream dadosFoto) {

        Long restauranteId = fotoProduto.getProduto().getRestaurante().getId();
        Long produtoId = fotoProduto.getProduto().getId();
        String nomeArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());
        String nomeFotoAntiga = null;

        Optional<FotoProduto> fotoProdutoOptional = produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoProdutoOptional.isPresent()) {
            nomeFotoAntiga = fotoProdutoOptional.get().getNomeArquivo();
            produtoRepository.delete(fotoProdutoOptional.get());
        }

        fotoProduto.setNomeArquivo(nomeArquivo);

        FotoProduto fotoSalva = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(dadosFoto)
                .contentType(fotoProduto.getContentType())
                .tamanhoArquivo(fotoProduto.getTamanho())
                .build();

        fotoStorageService.substituir(nomeFotoAntiga, novaFoto);

        return fotoSalva;
    }

    @Transactional
    public void delete(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = this.findById(restauranteId, produtoId);

        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();

        fotoStorageService.excluir(fotoProduto.getNomeArquivo());
    }
}
