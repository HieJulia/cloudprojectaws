package com.project.aws.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Slf4j
@Configuration
public class AWSClientConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region}")
    private String region;


    @Bean
    @Profile("aws")
    public AmazonSQS awsSQSClient(){
        return AmazonSQSClientBuilder.defaultClient();
    }

    @Bean
    @Profile("local")
    public AmazonSQS localstackSqsClient(){
        return AmazonSQSClientBuilder.standard()
                .withEndpointConfiguration(
                        // Local stack : Service endpoint : localstack - region : eu-west = 1
                        new AwsClientBuilder.EndpointConfiguration("http://localstack:4576","eu-west-1"))
                .build();
    }



    // Credentials
    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    // AWS S3 client
    @Bean
    public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
        AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
        amazonS3Client.setRegion(Region.getRegion(Regions.fromName(region)));
        return amazonS3Client;
    }

}