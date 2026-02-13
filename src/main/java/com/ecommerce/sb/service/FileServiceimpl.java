package com.ecommerce.sb.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceimpl implements FileService {

    @Override
    public String UploadImage(MultipartFile file) throws IOException {

        String newfilename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/",newfilename);

        Files.createDirectories(path.getParent());
        file.transferTo(path);

        return newfilename;

    }

}
