package com.eheiste.laureatnet.service.Impl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eheiste.laureatnet.service.FileStorageService;

@Service
public class LocalFileStorageServiceImpl implements FileStorageService {

    private Path uploadPath;

    @Autowired
    public LocalFileStorageServiceImpl(ResourceLoader resourceLoader, @Value("${file.upload-dir}") String uploadDir) {
        try {
            this.uploadPath = Paths.get(uploadDir);
            if (!Files.exists(this.uploadPath)) {
                Files.createDirectories(this.uploadPath);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> storeFile(MultipartFile file) {
        try {
            String cryptName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(cryptName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            Map<String, String> fileInfo = new HashMap<>();
            fileInfo.put(cryptName, file.getContentType());
            return fileInfo;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public Map<String, String> storeFiles(List<MultipartFile> files) {
        Map<String, String> fileInfos = new HashMap<>();
        for (MultipartFile file : files) {
            Map<String, String> fileInfo = storeFile(file);
            if (fileInfo != null) {
                fileInfos.putAll(fileInfo);
            }
        }
        return fileInfos;
    }

    @Override
    public byte[] getFileContent(String filename) {
        Path filePath = uploadPath.resolve(filename);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }
}