package com.itabrek.baseback.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUploadUtil {

//    @Value("${upload.path}")
    private static String uploadPath = "C:/Users/User/Documents/_PROJECTS/JAVA/hackatonback/src/main/resources/media";

    public static String saveFile(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "." + file.getContentType().split("/")[1];
        Path uploadPath = Paths.get(FileUploadUtil.uploadPath);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + filename, ioe);
        }

        return filename;
    }
}
