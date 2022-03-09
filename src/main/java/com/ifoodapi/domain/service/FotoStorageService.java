package com.ifoodapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);
    void excluir(String nomeArquivo);
    FotoRecuperada recuperar(String nomeArquivo);

    default  void substituir(String nomeFotoAntiga, NovaFoto novaFoto) {

        this.armazenar(novaFoto);

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
        private String contentType;
        private Long tamanhoArquivo;
    }

    @Builder
    @Getter
    public class FotoRecuperada {
        private URL url;
        private InputStream inputStream;

        public boolean temUrl() {
            return this.url != null;
        }

        public boolean temInputStream() {
            return this.inputStream != null;
        }
    }
}
