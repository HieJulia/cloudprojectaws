package com.project.aws.service;


import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import com.project.aws.config.Utils;
import com.project.aws.domain.crawler.EStatus;
import org.apache.commons.lang3.concurrent.ConcurrentUtils;

import java.util.HashMap;
import java.util.concurrent.Future;


public class BaseServiceTest {

    // Put Item Requests
    protected PutItemRequest putItemRequest(String url, EStatus status) {

        PutItemRequest putItemRequest = new PutItemRequest();
        putItemRequest.setTableName(Utils.table.websites);
        HashMap<String, AttributeValue> request = new HashMap<>();
        request.put(Utils.params.url, new AttributeValue(url));
        request.put(Utils.params.status, new AttributeValue(status.name()));
        putItemRequest.setItem(request);

        return putItemRequest;
    }


    // Put Item Results
    protected Future<PutItemResult> putItemResult(String url, EStatus status) {

        PutItemResult putItemResult = new PutItemResult();
        HashMap<String, AttributeValue> result = new HashMap<>();
        result.put(Utils.params.url, new AttributeValue(url));
        result.put(Utils.params.status, new AttributeValue(status.name()));
        putItemResult.setAttributes(result);

        return ConcurrentUtils.constantFuture(putItemResult);
    }




}
