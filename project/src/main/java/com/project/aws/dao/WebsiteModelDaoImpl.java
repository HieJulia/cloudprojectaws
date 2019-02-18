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
}
