package com.project.aws.config;


import com.amazonaws.AmazonClientException;
import com.project.aws.domain.crawler.WebsiteModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
@Configuration
public class DynamoDBConfig {


    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.profile}")
    private String awsProfile;

    @Value("${aws.dynamodb.endpoint.use}")
    private boolean useEndpoint;

    @Value("${aws.dynamodb.endpoint.host}")
    private String dynamoEndpointHost;

    @Value("${aws.dynamodb.endpoint.port}")
    private Integer dynamoEndpointPort;

    @Value("${aws.dynamodb.table.name}")
    private String tableName;




//    @Bean(name = "websitemodel")
//    public DynamoDbAdapterClient<WebsiteModel> amazonDynamoDBClient() {
//        return new DynamoDbAdapterConfiguration<WebsiteModel>()
//                .withAwsRegion(awsRegion)
//                .withAwsProfile(awsProfile)
//                .withHost(dynamoEndpointHost)
//                .withPort(dynamoEndpointPort)
//                .withTableName(tableName)
//                .withUseEndpoint(useEndpoint)
//                .withAmazonDynamoDBClient();
//    }



    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());
//        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
//            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
//        }
        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        AWSCredentials credentials;
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch(Exception e) {
            throw new AmazonClientException(e);
        }
        return credentials;

    }


}




