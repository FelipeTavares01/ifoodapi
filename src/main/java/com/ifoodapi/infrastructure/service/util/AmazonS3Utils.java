package com.ifoodapi.infrastructure.service.util;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ifoodapi.core.storage.StorageProperties;
import com.ifoodapi.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmazonS3Utils {

    @Autowired
    private StorageProperties storageProperties;

    public PutObjectRequest getPutObjectRequest(FotoStorageService.NovaFoto novaFoto) {
        var caminhoArquivo = getCaminhoArquivo(storageProperties.getS3().getDiretorioFotos(),
                novaFoto.getNomeArquivo());

        var objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(novaFoto.getContentType());
        objectMetadata.setContentLength(novaFoto.getTamanhoArquivo());

        var putObjectRequest = new PutObjectRequest(
                storageProperties.getS3().getBucket(),
                caminhoArquivo,
                novaFoto.getInputStream(),
                objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);

        return putObjectRequest;
    }

    public DeleteObjectRequest getDeleteObjectRequest(String nomeArquivo) {

        var caminhoArquivo = getCaminhoArquivo(storageProperties.getS3().getDiretorioFotos(),
                nomeArquivo);

        var deleteObjectRequest = new DeleteObjectRequest(
                storageProperties.getS3().getBucket(),
                caminhoArquivo);

        return deleteObjectRequest;
    }



    public String getCaminhoArquivo(String diretorioFotos, String nomeArquivo) {
        return String.format("%s/%s", diretorioFotos, nomeArquivo);
    }
}
