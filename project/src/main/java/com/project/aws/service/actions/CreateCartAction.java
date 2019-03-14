package com.project.aws.service.actions;


import com.project.aws.domain.request.LambdaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

import javax.naming.Context;


public class CreateCartAction extends AbstractAction{

    static final Logger LOGGER = LoggerFactory.getLogger(CreateCartAction.class);

    private static final String LOGINID = "loginId";

    private static final String SKU = "sku";

    private static final String SUCCESS = "200";


    @Override
    public String handle(LambdaRequest lambdaRequest, Context context) {
        // Validate request
        validateRequest(lambdaRequest);
        // Con lin nay y chang con trai luon - Ke me tao - No biet day - No kho chiu voi thang lin nay
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
            LOGGER.info("No loginId param present");
        }
        if(!lambdaRequest.getBody().has(SKU)) {
            LOGGER.info("No sku param present");
        }
        if(StringUtils.isEmpty(lambdaRequest.getBody().get(LOGINID).getAsString())){
            LOGGER.info("Empty loginID present");
        }

        if(StringUtils.isEmpty(lambdaRequest.getBody().get(SKU).getAsString())){
            LOGGER.info("Empty sku present");
        }

    }

}
