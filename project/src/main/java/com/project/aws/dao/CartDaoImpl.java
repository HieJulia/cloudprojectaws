package com.project.aws.dao;


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


public class CartDaoImpl implements CartDao {


    static final Logger LOGGER = LoggerFactory.getLogger(CartDaoImpl.class);

    private static CartDaoImpl instance = null;

    // credentials for the client come from the environment variables pre-configured by Lambda. These are tied to the
    // Lambda function execution role.new BasicAWSCredentials(amazonAWSAccessKey,amazonAWSSecretKey)
    private static AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient();



    /**
     * Returns the initialized default instance of the PetDAO
     *
     * @return An initialized PetDAO instance
     */
    public static CartDaoImpl getInstance() {
        if (instance == null) {
            instance = new CartDaoImpl();
        }

        return instance;
    }

    protected CartDAOImpl() {
        // constructor is protected so that it can't be called from the outside
    }

    /**
     * Returns a DynamoDBMapper object initialized with the default DynamoDB client
     *
     * @return An initialized DynamoDBMapper
     */
    protected DynamoDBMapper getMapper() {
        return new DynamoDBMapper(ddbClient);
    }

    @Override
    public Cart getCartByState(String cartState) throws DAOException {
        return getMapper().load(Cart.class, cartState);
    }

    @Override
    public List<Cart> getCartByLoginId(String loginId) throws DAOException {
        //QUERY EXPRESSION TO PULL ONLY OPEN ITEM STATE
        Cart cartKey = new Cart();
        cartKey.setLoginId(loginId);

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":val", new AttributeValue().withS("OPEN"));

        DynamoDBQueryExpression<Cart> queryExpression = new DynamoDBQueryExpression<Cart>()
                .withHashKeyValues(cartKey)
                .withFilterExpression("itemState = :val")
                .withExpressionAttributeValues(expressionAttributeValues);

        List<Cart> cartCollection = getMapper().query(Cart.class, queryExpression);
        return cartCollection;
    }

    @Override
    public String createCart(Cart cart) throws DAOException {
        getMapper().save(cart, new DynamoDBMapperConfig(SaveBehavior.UPDATE));
        return cart.getLoginId();
    }

    @Override
    public void deleteItem(Cart cart) throws DAOException {
        if(StringUtils.isEmpty(cart.getSku())){
            deleteAll(cart);
        }
        if(!StringUtils.isEmpty(cart.getSku())){
            getMapper().delete(cart);
        }
    }

    @Override
    public void deleteAll(Cart cart) throws DAOException {
        //IF SKU IS NOT PRESENT LIST ALL ITEM AND THEN DELETE THEM
        List<Cart> cartCollection = getCartByLoginId(cart.getLoginId());
        for(Cart cct : cartCollection){
            Cart ct = new Cart();
            ct.setLoginId(cct.getLoginId());
            ct.setSku(cct.getSku());
            getMapper().delete(ct);
        }
    }
}


