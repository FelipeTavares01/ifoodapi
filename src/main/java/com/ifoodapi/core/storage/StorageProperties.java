package com.ifoodapi.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("ifood.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private TipoStorage tipo;

    @Getter
    public enum TipoStorage {
        LOCAL, S3;
    }

    @Getter
    @Setter
    public class Local {
        private Path diretorioFotos;

    }

    @Getter
    @Setter
    public class S3 {
        private String accessKey;
        private String secretKey;
        private String bucket;
        private Regions regiao;
        private String diretorioFotos;
    }

}
