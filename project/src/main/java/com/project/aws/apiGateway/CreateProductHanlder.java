package com.project.aws.apiGateway;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.aws.dao.ProductDao;
import com.project.aws.domain.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;



public class CreateProductHanlder implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        try {

            // Create product - Product - JSON

            // Json node - read tree - body

            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));


            Product newProduct = new Product();
            // From json -
            newProduct.setName(body.get("name").asText());
            newProduct.setPrice((float) body.get("price").asDouble());
            newProduct.setReview(body.get("review").asText());

            // Instantite DAO
            ProductDao productDao = new ProductDao();

            // Save new product
            productDao.save(newProduct);

            // Return API gateway response
            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(newProduct)
                    .build();

        } catch (Exception e) {
            //
            Response responseBody = new Response("Error in saving product: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
    }

}
