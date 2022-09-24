package com.example.springbootminiostoragelearning.service;

import com.example.springbootminiostoragelearning.listener.TestBucketListener;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Event;
import io.minio.messages.NotificationRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
   MinioClient minioClient;

    @Value("${minio.bucket.name}")
    String defaultBucketName;

    @Autowired
    TestBucketListener testBucketListener;


    @Override
    public List<Bucket> getAllBuckets() throws Exception {
        return minioClient.listBuckets();
    }
     @Override
    public String createBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            return bucketName + " bucket created";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public byte[] getFile(String objectName,String bucketName) {
        try {
            InputStream file = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName).build());
            testBucketListener.run();
            return file.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public MultipartFile uploadFile(MultipartFile file,String bucketName) throws IOException {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(),file.getSize(),-1).build());
            testBucketListener.run();
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e);
        }

        return file;
    }

}
