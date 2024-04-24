package com.itabrek.baseback.controller;


import org.overviewproject.mime_types.GetBytesException;
import org.overviewproject.mime_types.MimeTypeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/media")
public class MediaController {
    @Value("${upload.path}")
    private String mediaPath;

    private static final Logger logger = LoggerFactory.getLogger(MediaController.class);

    @GetMapping("/{mediaName}")
    public ResponseEntity getMedia(@PathVariable String mediaName) throws IOException, GetBytesException {
        try {
            logger.info("Start get_media execution");
            Path imagePath = Paths.get(mediaPath, mediaName);

            byte[] fileBytes = Files.readAllBytes(imagePath);

            final var mimeTypeDetector = new MimeTypeDetector();
            final var mimeType = mimeTypeDetector.detectMimeType(mediaName, () -> fileBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.asMediaType(MimeType.valueOf(mimeType)));

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileBytes);
        } catch (Exception e) {
            logger.info("Unable to send file", e);
            return new ResponseEntity<>("Unable to load file", HttpStatus.NOT_FOUND);
        }
    }
}
