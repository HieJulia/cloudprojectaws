package com.project.aws.service.sqslambda.service;

public interface ConsumerConfig {


    // Get queue name
    String getQueueName();


    // Get process messages max
    int getProcessMessagesMax();


    // Get worker
    String getWorkerLambdaName();


}

