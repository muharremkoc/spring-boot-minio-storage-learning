package com.example.springbootminiostoragelearning.controller;

import com.example.springbootminiostoragelearning.service.MinioService;
import com.example.springbootminiostoragelearning.service.MinioServiceImpl;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/buckets")
@RequiredArgsConstructor
public class BucketController {

    final MinioService minioService;


    @GetMapping(path = "")
    public List<Bucket> getAllBuckets() throws Exception {
        return minioService.getAllBuckets();
    }

    @GetMapping(path = "/{bucketName}")
    public String createBucket(@PathVariable String bucketName){
        return minioService.createBucket(bucketName);
    }
}
