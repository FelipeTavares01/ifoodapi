package com.ifoodapi.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.ifoodapi.core.storage.StorageProperties;
import com.ifoodapi.domain.service.FotoStorageService;
import com.ifoodapi.infrastructure.service.exception.StorageException;
import com.ifoodapi.infrastructure.service.util.AmazonS3Utils;
import org.springframework.beans.factory.annotation.Autowired;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private AmazonS3Utils amazonS3Utils;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {

        try {
            var putObjectRequest = amazonS3Utils.getPutObjectRequest(novaFoto);
            amazonS3.putObject(putObjectRequest);

        } catch (Exception e) {
            throw  new StorageException("Não foi possivel armazenar a foto na Amazon S3", e);
        }
    }

    @Override
    public void excluir(String nomeArquivo) {
        try {
            var deleteObjectRequest = amazonS3Utils.getDeleteObjectRequest(nomeArquivo);
            amazonS3.deleteObject(deleteObjectRequest);

        } catch (Exception e) {
            throw new StorageException("Não foi possivel deletar a foto na Amazon S3", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            var caminhoArquivo = amazonS3Utils.getCaminhoArquivo(storageProperties.getS3().getDiretorioFotos(),
                    nomeArquivo);

            var url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

            var fotoRecuperada = FotoRecuperada.builder()
                    .url(url)
                    .build();

            return fotoRecuperada;

        } catch (Exception e) {
            throw new StorageException("Não foi possivel recuperar a foto na Amazon S3");
        }
    }


}
