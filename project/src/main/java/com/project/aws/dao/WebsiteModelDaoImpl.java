package com.project.aws.dao;

import com.project.aws.domain.crawler.WebsiteModel;

import java.util.List;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;


public class WebsiteModelDaoImpl implements WebsiteModelDao{

    // AWS credentials

    // credentials for the client come from the environment variables pre-configured by Lambda. These are tied to the
    // Lambda function execution role.new BasicAWSCredentials(amazonAWSAccessKey,amazonAWSSecretKey)
    private static AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient();

    // Con be nay thong minh vcl ra
    




    @Override
    public WebsiteModel getByUrl(String url) {
        return null;
    }

    @Override
    public List<WebsiteModel> getAll() {
        return null;
    }

    @Override
    public void create(WebsiteModel websiteModel) {

    }

    @Override
    public void deleteOne(WebsiteModel websiteModel) {

    }


    // ngoi chui tui troi oi -



    // sao con nho nay han thong minh ghe nhi - may khong qua duoc dan chau a dau



    // No van binh thuong ma


    // Gui lai cho no



}
