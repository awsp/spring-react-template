package com.areamode.project.service;

import com.areamode.project.config.MinioConfiguration;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;


@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    private final MinioConfiguration configuration;

    public byte[] getFile(String key) {
        GetObjectArgs objectArgs = GetObjectArgs.builder()
                .bucket(configuration.getBucketName())
                .object(key)
                .build();
        try {
            InputStream object = minioClient.getObject(objectArgs);
            byte[] content = IOUtils.toByteArray(object);
            object.close();

            return content;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadFile(String name, byte[] content, String contentType) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .contentType(contentType)
                    .bucket(configuration.getBucketName())
                    .object(name)
                    .stream(new ByteArrayInputStream(content), content.length, -1)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
