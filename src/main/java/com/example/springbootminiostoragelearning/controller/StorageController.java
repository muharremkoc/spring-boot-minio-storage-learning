package com.example.springbootminiostoragelearning.controller;

import com.example.springbootminiostoragelearning.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {

    final MinioService minioService;

    @GetMapping(path = "/object/{objectName}")
    public ResponseEntity<Resource> getBucketsImages(@PathVariable String objectName,@RequestParam String bucketName) {
        var data = minioService.getFile(objectName,bucketName);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+objectName+"\"")
                .body(new ByteArrayResource(data));
    }

    @PostMapping(path = "/upload/{bucketName}", consumes = {"multipart/form-data"})
    public ResponseEntity<Resource> savePostPhoto(@PathVariable("bucketName")String bucketName, @RequestPart("file") MultipartFile file) throws IOException {
        var image = minioService.uploadFile(file,bucketName);
        return ResponseEntity.ok().contentType(MediaType.valueOf(Objects.requireNonNull(image.getContentType())))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getOriginalFilename() + "\"")
                .body(new ByteArrayResource(image.getBytes()));
    }
}
