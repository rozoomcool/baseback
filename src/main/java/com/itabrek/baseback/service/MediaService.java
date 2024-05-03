package com.itabrek.baseback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MediaService {
    private static final String MEDIA_PATH = "classpath:media/";
    private final ResourceLoader resourceLoader;

    public Mono<ResponseEntity<Resource>> getMedia(String title) {
        return Mono.fromSupplier(() -> {
            Resource resource = resourceLoader.getResource(MEDIA_PATH + title);
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM));
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        });
    }
}
