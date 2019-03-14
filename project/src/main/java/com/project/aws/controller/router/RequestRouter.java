package com.project.aws.controller.router;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.project.aws.domain.request.LambdaRequest;
import com.project.aws.service.actions.Action;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.naming.Context;


public class RequestRouter {


    static final Logger LOGGER = LoggerFactory.getLogger(RequestRouter.class);

    private static final String ACTIONCLASS = "resourcePath";



    public static void lambdaHandler(InputStream request, OutputStream response, Context context) {

        //Initialize the context logger

        // Init context logger

        // JsonParser

        JsonParser parser = new JsonParser();

        JsonObject inputObj;
        try {
            // Parse object
            inputObj = parser.parse(IOUtils.toString(request)).getAsJsonObject();
        } catch (Exception e) {


        }



        //Validate InputStream Request
        validateServiceObject(inputObj);

        //LOAD THE CONTEXT PROPERTIES
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/*.xml");

        String actionClass = getActionClass(applicationContext,inputObj);

        applicationContext.close();

        Action action = null;

        try {
            // Action


            // No khong thuc

            action = Action.class.cast(Class.forName(actionClass).newInstance());

        } catch (final InstantiationException e) {


        } catch (final IllegalAccessException e) {

        } catch (final ClassNotFoundException e) {

        }

        if (action == null) {

        }

        String json_string = new Gson().toJson(inputObj);

        LambdaRequest lambdaRequest = new Gson().fromJson(json_string, LambdaRequest.class);


        // Nay nay no rang no kiem che do mi - Ta biet cai tinh con lin nay roi - No se boc phat day
        String output = action.handle(lambdaRequest, context);//action.handle(inputObj, context);

        try {

            IOUtils.write(output, response);

        } catch (final IOException e) {


        }
    }


    // Get action class
    @SuppressWarnings("unchecked")
    private static String getActionClass(ConfigurableApplicationContext applicationContext,
                                         JsonObject inputObj) {

        Map<String,Object> beanMapping = (Map<String, Object>) applicationContext.getBean("apiMap");
        String actionName = inputObj.get(ACTIONCLASS).getAsString();
        String actionBean ="";
        for (Map.Entry<String, Object> beanMap : beanMapping.entrySet()) {
            if(actionName.contains(beanMap.getKey())) {
                actionBean = beanMap.getValue().toString();
                break;
            }
        }

        return actionBean;

    }


    // Validate service object
    private static void validateServiceObject(JsonObject inputObj) {

        if(ObjectUtils.isEmpty(inputObj) || StringUtils.isEmpty(inputObj.get(ACTIONCLASS))){
        }

    }

}








