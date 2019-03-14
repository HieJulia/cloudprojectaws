package com.project.aws.service.actions;


import java.util.ArrayList;
import java.util.List;

import com.project.aws.domain.request.LambdaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


import com.google.gson.Gson;

import javax.naming.Context;


public class ReadCartAction extends AbstractAction{

    static final Logger LOGGER = LoggerFactory.getLogger(ReadCartAction.class);

    private static final String LOGINID = "loginId";

    private static final String SUCCESS = "200";

    //



    @Override
    public String handle(LambdaRequest lambdaRequest, Context context) {


        // Validate request
        validateRequest(lambdaRequest);

        // Get login ID
        String loginId = lambdaRequest.getQuery().get(LOGINID).getAsString();


        // Get Cart
        CartDAO dao = DynamoDBManager.getCartDAO();

        List<Cart> cartList = new ArrayList<Cart>();

        try {
            // Get cart by login id
            cartList = dao.getCartByLoginId(loginId);
        } catch (Exception e) {

        }

        ReadCartResponse readCartResponse = new ReadCartResponse();
        readCartResponse.setLoginId(loginId);
        readCartResponse.setCart(cartList);
        readCartResponse.setCode(SUCCESS);

        return new Gson().toJson(readCartResponse);

    }


    /**
     * Validate request
     */
    private void validateRequest(LambdaRequest lambdaRequest){

        // Query = null
        if(lambdaRequest.getQuery() == null){

        }

        if(!lambdaRequest.getQuery().has(LOGINID)) {

        }
        if(StringUtils.isEmpty(lambdaRequest.getQuery().get(LOGINID).getAsString())){

        }


    }

}
