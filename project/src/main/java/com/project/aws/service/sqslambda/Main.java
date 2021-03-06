package com.project.aws.service.sqslambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.devfront.aws.sqslambda.consumer.component.ConsumerComponent;
import net.devfront.aws.sqslambda.consumer.component.DaggerConsumerComponent;

public class Main implements RequestHandler<Object, Object> {

    // Lambda request - handler
    private final ConsumerComponent consumerComponent;


    public Main(){
        consumerComponent = DaggerConsumerComponent.builder().build();
    }

    @Override
    public Object handleRequest(Object o, Context context) {
        consumerComponent.getDequeueService().run();
        return null;
    }
}