package com.project.aws.service.s3;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Configuration class
 *
 * Thich di
 */

@Configuration
public class S3ServiceConfig {

    // S3 : access_key, secret_key, bucketname, region , bucketUrl


    // No dau co quan tam lam dau



    @Value("${amazon.access_key_id}")
    private String access_key;

    @Value("${amazon.secret_access_key}")
    private String secret_key;

    @Value("${amazon.bucketName}")
    private String bucketName;

    @Value("${amazon.region}")
    private String region;

    @Value("${amazon.bucketURL}")
    private String bucketURL;


    // Config Bean : s3 client


    @Bean
    public AmazonS3 s3client() {

        // AWS credentials
        AWSCredentials awsCreds = new BasicAWSCredentials(access_key, secret_key);

        // AWS S3 client - Gia do - sau do cuoi - the thoi
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        return s3Client;
    }
}