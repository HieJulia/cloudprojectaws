package com.project.aws.config;


import com.project.aws.domain.crawler.WebsiteModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


    // Choc no tuc len roi day - de xem ai hon ai




    // Nay tui bay tuong tui bay hon duoc cai dau cua dan chau a a - tui no hoc cao hieu rong



    // Du me may tuong tao la ai vay - thang tieu nhan nay





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



}
