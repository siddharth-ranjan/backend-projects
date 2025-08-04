package com.hackathon.markdownnotetakingapp.service.storage;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {

    ResponseEntity<String> save(MultipartFile file);
    String loadFileAsString(String filename);
}