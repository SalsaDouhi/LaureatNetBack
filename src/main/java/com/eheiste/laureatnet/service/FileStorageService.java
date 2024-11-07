package com.eheiste.laureatnet.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	Map<String, String> storeFile(MultipartFile file);
	Map<String, String> storeFiles(List<MultipartFile> files);
    byte[] getFileContent(String filename);

}