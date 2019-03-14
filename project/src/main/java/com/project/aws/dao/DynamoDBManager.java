package com.project.aws.dao;


import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

public class DynamoDBManager {

    // credentials for the client come from the environment variables pre-configured by Lambda. These are tied to the
    // Lambda function execution role.new BasicAWSCredentials(amazonAWSAccessKey,amazonAWSSecretKey)
    private static AmazonDynamoDBClient dynamoDB;

    public static void init() throws Exception{
        dynamoDB = new AmazonDynamoDBClient();
    }


    // Save
    public static void save(Object entity) {
        // Mapper - save
        // Validate entity
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);

        mapper.save(entity);
    }

    // Query
    public static <T> PaginatedList<T> query(Class<T> klass, DynamoDBQueryExpression query) {
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
        PaginatedList<T> list = mapper.query(klass, query);
        return list;
    }

    // Get object
    public static <T> T get(Class<T> klass, DynamoDBQueryExpression query) {
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
        PaginatedList<T> list = mapper.query(klass, query);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static List<String> tableNames() {
        ListTablesResult tables = dynamoDB.listTables();
        List<String> result = new ArrayList<String>();
        for (String tableName : tables.getTableNames()) {
            result.add(tableName);
        }
        return result;
    }

    /**
     * Contains the implementations of the DAO objects. By default we only have a DynamoDB implementation
     */
    public enum DAOType {
        DynamoDB
    }




}
