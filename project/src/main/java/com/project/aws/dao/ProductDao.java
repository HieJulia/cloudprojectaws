//package com.project.aws.dao;
//
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.datamodeling.*;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//import com.project.aws.domain.Product;
//
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//
//
//public class ProductDao {
//
//
//    private static final String PRODUCTS_TABLE_NAME = "PRODUCTS_TABLE";
//
//    DynamoDBAdapter db_adapter;
//    DynamoDBMapper mapper;
//    AmazonDynamoDB client;
//
//    // Constructor
//    public ProductDao() {
//        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
//                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PRODUCTS_TABLE_NAME))
//                .build();
//        this.db_adapter = DynamoDBAdapter.getInstance();
//        this.client = this.db_adapter.getDbClient();
//        this.mapper = this.db_adapter.createDbMapper(mapperConfig);
//    }
//
//    // List all products in the DynamoDB database
//    public List<Product> list() {
//        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
//        List<Product> results = this.mapper.scan(Product.class, scanExp);
//        for (Product p : results) {
//            results.add(p);
//        }
//        return results;
//    }
//
//    // GET product by id
//    public Product get(String id) throws IOException {
//        Product product = null;
//
//        HashMap<String, AttributeValue> av = new HashMap<String, AttributeValue>();
//        av.put(":v1", new AttributeValue().withS(id));
//
//        DynamoDBQueryExpression<Product> queryExp = new DynamoDBQueryExpression<Product>()
//                .withKeyConditionExpression("id = :v1")
//                .withExpressionAttributeValues(av);
//
//        PaginatedQueryList<Product> result = this.mapper.query(Product.class, queryExp);
//        if (result.size() > 0) {
//            product = result.get(0);
//
//        } else {
//
//        }
//        return product;
//
//        // Su dung aws- create o tren command line
//
//    }
//
//    // Get product by name
//    public Product getByName(String name) throws IOException {
//        Product returnedProduct = null;
//
//        HashMap<String, AttributeValue> av = new HashMap<String, AttributeValue>();
//        av.put(":v1", new AttributeValue().withS(name));
//
//
//        // Query expression - key condition - express - name
//        DynamoDBQueryExpression<Product> queryExp = new DynamoDBQueryExpression<Product>()
//                .withKeyConditionExpression("name = :v1")
//                .withExpressionAttributeValues(av);
//
//        PaginatedQueryList<Product> result = this.mapper.query(Product.class, queryExp);
//
//        if (result.size() > 0) {
//            returnedProduct = result.get(0);
//
//        }
//        return returnedProduct;
//
//    }
//
//
//    // Save product
//    public Product save(Product product) throws IOException {
//
//        this.mapper.save(product);
//
//        return product;
//    }
//
//
//    // Delete product
//
//    public void delete(String id) throws IOException {
//        Product product = null;
//
//        // get product if exists
//        product = get(id);
//        if (product != null) {
//            this.mapper.delete(product);
//        } else {
//
//         // Log
//        }
//    }
//
//
//
//
//
//
//}
