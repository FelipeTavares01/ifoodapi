package com.ifoodapi.infrastructure.service;

import com.ifoodapi.domain.service.FotoStorageService;
import com.ifoodapi.infrastructure.service.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${ifood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

    @Override
    public void armanezar(NovaFoto novaFoto) {

        try {
            var arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));

        } catch (IOException e) {
            throw new StorageException("Não foi possivel armanezar o arquivo", e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }
}
