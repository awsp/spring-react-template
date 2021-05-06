package com.github.awsp.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfiguration {

    private String accessKey;
    private String accessSecret;
    private String endpoint;
    private String bucketName;

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .credentials(accessKey, accessSecret)
                .endpoint(endpoint)
                .build();
    }
}