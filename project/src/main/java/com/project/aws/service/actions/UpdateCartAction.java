package com.project.aws.service.actions;

import com.project.aws.domain.request.LambdaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


import com.google.gson.Gson;

import javax.naming.Context;


public class UpdateCartAction extends AbstractAction{

    static final Logger LOGGER = LoggerFactory.getLogger(UpdateCartAction.class);

    private static final String LOGINID = "loginId";

    private static final String SKU = "sku";

    private static final String SUCCESS = "200";



    // No dap may chet
    @Override
    public String handle(LambdaRequest lambdaRequest, Context context) {
        validateRequest(lambdaRequest);

        Cart cartBody = new Gson().fromJson(lambdaRequest.getBody(), Cart.class);

        CartDAO dao = DynamoDBManager.getCartDAO();

        try {
            dao.createCart(cartBody);
        } catch (Exception e) {
            LOGGER.info("Error while creating the cart" + e.getMessage());
        }

        ReadCartResponse readCartResponse = new ReadCartResponse();
        readCartResponse.setLoginId(cartBody.getLoginId());
        readCartResponse.setCode(SUCCESS);

        return new Gson().toJson(readCartResponse);
    }

    private void validateRequest(LambdaRequest lambdaRequest){
        if(!lambdaRequest.getBody().has(LOGINID)) {
        }
        if(!lambdaRequest.getBody().has(SKU)) {
        }
        if(StringUtils.isEmpty(lambdaRequest.getBody().get(LOGINID).getAsString())){
        }

        if(StringUtils.isEmpty(lambdaRequest.getBody().get(SKU).getAsString())){
        }

    }

}
