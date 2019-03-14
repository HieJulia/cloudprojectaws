//package com.project.aws.dao;
//
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//
//
//public class CartDaoImpl implements CartDao {
//
//
//    static final Logger LOGGER = LoggerFactory.getLogger(CartDaoImpl.class);
//
//    private static CartDaoImpl instance = null;
//
//    // credentials for the client come from the environment variables pre-configured by Lambda. These are tied to the
//    // Lambda function execution role.new BasicAWSCredentials(amazonAWSAccessKey,amazonAWSSecretKey)
//    private static AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient();
//
//
//    // Credentials - AWS DynamoDBclient
//
//
//
//
//
//    /**
//     * Returns the initialized default instance of the PetDAO
//     *
//     * @return An initialized PetDAO instance
//     */
//    public static CartDaoImpl getInstance() {
//        if (instance == null) {
//            instance = new CartDaoImpl();
//        }
//
//        return instance;
//    }
//
//
//    /**
//     * Returns a DynamoDBMapper object initialized with the default DynamoDB client
//     *
//     * @return An initialized DynamoDBMapper
//     */
//    protected DynamoDBMapper getMapper() {
//        return new DynamoDBMapper(ddbClient);
//    }
//
//
//    // Get cart by state
//    @Override
//    public Cart getCartByState(String cartState) {
//        // Mapper - load - Cart
//        return getMapper().load(Cart.class, cartState);
//
//        // Cua bon thuy dien
//    }
//
//
//    // Get cart by login id
//    @Override
//    public List<Cart> getCartByLoginId(String loginId) {
//        //QUERY EXPRESSION TO PULL ONLY OPEN ITEM STATE
//        Cart cartKey = new Cart();
//        cartKey.setLoginId(loginId);
//
//        Map<String, AttributeValue> expressionAttributeValues = new HashMap<String, AttributeValue>();
//        expressionAttributeValues.put(":val", new AttributeValue().withS("OPEN"));
//
//        DynamoDBQueryExpression<Cart> queryExpression = new DynamoDBQueryExpression<Cart>()
//                .withHashKeyValues(cartKey)
//                .withFilterExpression("itemState = :val")
//                .withExpressionAttributeValues(expressionAttributeValues);
//
//        List<Cart> cartCollection = getMapper().query(Cart.class, queryExpression);
//        return cartCollection;
//    }
//
//    @Override
//    public String createCart(Cart cart){
//        getMapper().save(cart, new DynamoDBMapperConfig(SaveBehavior.UPDATE));
//        return cart.getLoginId();
//    }
//
//    @Override
//    public void deleteItem(Cart cart){
//        if(StringUtils.isEmpty(cart.getSku())){
//            deleteAll(cart);
//        }
//        if(!StringUtils.isEmpty(cart.getSku())){
//            getMapper().delete(cart);
//        }
//    }
//
//
//    // Delete all data in DynamoDB table
//    @Override
//    public void deleteAll(Cart cart){
//        //IF SKU IS NOT PRESENT LIST ALL ITEM AND THEN DELETE THEM
//        List<Cart> cartCollection = getCartByLoginId(cart.getLoginId());
//        // Get
//        for(Cart cct : cartCollection){
//            Cart ct = new Cart();
//            ct.setLoginId(cct.getLoginId());
//            ct.setSku(cct.getSku());
//            getMapper().delete(ct);
//        }
//    }
//}
//
//// Ko thay no dap ban dap ghe
//
//
