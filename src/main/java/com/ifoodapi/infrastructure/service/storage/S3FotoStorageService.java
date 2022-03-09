package com.ifoodapi.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.ifoodapi.domain.service.FotoStorageService;
import com.ifoodapi.infrastructure.service.exception.StorageException;
import com.ifoodapi.infrastructure.util.AmazonS3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private AmazonS3Utils amazonS3Utils;

    @Override
    public void armanezar(NovaFoto novaFoto) {

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
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }


}
