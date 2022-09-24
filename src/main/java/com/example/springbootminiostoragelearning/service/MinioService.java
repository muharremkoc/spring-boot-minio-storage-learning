package com.example.springbootminiostoragelearning.service;

import io.minio.messages.Bucket;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MinioService {

    List<Bucket> getAllBuckets() throws Exception;
    String createBucket(String bucketName);
    byte[] getFile(String objectName,String bucketName);
    MultipartFile uploadFile(MultipartFile file,String bucketName) throws IOException;
}
