package com.project.aws.service.actions;


import com.project.aws.domain.request.LambdaRequest;

import javax.naming.Context;

public interface Action {

    // Handle Lambda request

    String handle(LambdaRequest lambdaRequest, Context context);

}


