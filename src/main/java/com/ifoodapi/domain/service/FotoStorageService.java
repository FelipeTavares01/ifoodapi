package com.ifoodapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

public interface FotoStorageService {

    void armanezar(NovaFoto novaFoto);

    void excluir(String nomeArquivo);

    default  void substituir(String nomeFotoAntiga, NovaFoto novaFoto) {

        this.armanezar(novaFoto);

        if(Objects.nonNull(nomeFotoAntiga)) {
            this.excluir(nomeFotoAntiga);
        }
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    @Builder
    @Getter
    public class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
