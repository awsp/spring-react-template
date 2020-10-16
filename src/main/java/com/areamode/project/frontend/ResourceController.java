package com.areamode.project.frontend;

import com.areamode.project.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/minio")
@RequiredArgsConstructor
public class ResourceController {

    private final MinioService minioService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Resource index for testing...");
    }

    @GetMapping("/show-image")
    public ResponseEntity<byte[]> showImage() {
        // Hard-coded for now
        final String file = "image.png";
        byte[] data = minioService.getFile(file);

        // Check for object existence and response with different HTTP status.

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(URLConnection.guessContentTypeFromName(file)));
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @GetMapping("/download-image")
    public ResponseEntity<ByteArrayResource> downloadImage() {
        // Hard-coded for now
        final String file = "image.png";
        byte[] data = minioService.getFile(file);

        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file + "\"")
                .body(byteArrayResource);
    }

    @PostMapping(path = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> uploadImage(@RequestPart(value = "file") MultipartFile file) throws IOException {
        // Should validate upload type here!

        // Upload to bucket
        minioService.uploadFile(file.getOriginalFilename(), file.getBytes(), file.getContentType());
        Map<String, String> result = new HashMap<>();
        result.put("key", file.getOriginalFilename());
        return result;
    }
}
