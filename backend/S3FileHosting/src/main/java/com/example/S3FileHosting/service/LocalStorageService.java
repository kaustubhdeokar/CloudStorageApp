package com.example.S3FileHosting.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalStorageService {
    public String uploadFile(MultipartFile file) {
        return "File uploaded successfully.";
    }

    public byte[] downloadFile(String fileName) {
        return new byte[0];
    }

    public String deleteFile(String fileName) {
        return "File deleted successfully!";
    }
}
