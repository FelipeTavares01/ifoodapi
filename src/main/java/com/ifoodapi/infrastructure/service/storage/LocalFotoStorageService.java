package com.ifoodapi.infrastructure.service.storage;

import com.ifoodapi.core.storage.StorageProperties;
import com.ifoodapi.domain.service.FotoStorageService;
import com.ifoodapi.infrastructure.service.exception.StorageException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armanezar(NovaFoto novaFoto) {

        try {
            var arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));

        } catch (IOException e) {
            throw new StorageException("Não foi possivel armanezar o arquivo", e);
        }
    }

    @Override
    public void excluir(String nomeArquivo) {
        try {
            var arquivoPath = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e ) {
            throw new StorageException("Não foi possivel deletar o arquivo", e);
        }
    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            var arquivoPath = getArquivoPath(nomeArquivo);
            return Files.newInputStream(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possivel recuperar o arquivo", e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo)    );
    }
}
