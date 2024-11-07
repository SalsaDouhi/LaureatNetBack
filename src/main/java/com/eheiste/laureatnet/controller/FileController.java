package com.eheiste.laureatnet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.eheiste.laureatnet.service.FileStorageService;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename) {
    	byte[] fileContent = fileStorageService.getFileContent(filename);
    	if(fileContent==null) {
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        HttpHeaders headers = new HttpHeaders();
        if(filename.endsWith(".pdf")) {
        	headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("inline")
                    .filename(filename)
                    .build());        
        }else if(filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".gif")) {
            headers.setContentType(MediaType.IMAGE_JPEG); // or MediaType.IMAGE_PNG, etc.
            headers.setContentDisposition(ContentDisposition.inline().filename(filename).build());
        }else {
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);
        }
        headers.setContentLength(fileContent.length);
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
    /*@PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        fileStorageService.storeFile(file);
    }*/

    // Add other methods as needed
}