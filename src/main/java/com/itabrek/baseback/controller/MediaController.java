package com.itabrek.baseback.controller;


import com.itabrek.baseback.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.overviewproject.mime_types.GetBytesException;
import org.overviewproject.mime_types.MimeTypeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {
    @Value("${upload.path}")
    private String mediaPath;

    private final MediaService mediaService;

    private static final Logger logger = LoggerFactory.getLogger(MediaController.class);

    @GetMapping("/{mediaName}")
    public Mono<ResponseEntity<Resource>> getMedia(@PathVariable String mediaName) {
        return mediaService.getMedia(mediaName);
    }
}
