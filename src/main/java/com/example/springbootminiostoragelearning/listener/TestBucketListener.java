package com.example.springbootminiostoragelearning.listener;

import io.minio.ListenBucketNotificationArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestBucketListener {

    @Autowired
    MinioClient minioClient;

    public void run() {
        String[] events = {"s3:ObjectCreated:*", "s3:ObjectRemoved:*","s3:ObjectAccessed:*"};
        try {
            minioClient.listenBucketNotification(ListenBucketNotificationArgs.builder()
                    .bucket("postgrebucket")
                    .events(events)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }};


}
