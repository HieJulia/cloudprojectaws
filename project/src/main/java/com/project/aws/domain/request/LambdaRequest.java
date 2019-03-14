package com.project.aws.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.JsonObject;
import lombok.Data;

import java.io.Serializable;



@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class LambdaRequest implements Serializable {


    // LambdaRequest : body, param, query,


    private static final long serialVersionUID = 1L;

    private JsonObject body;

    private JsonObject params;

    private JsonObject query;

    private JsonObject headers;

    private String method;

    private String httpMethod;

    private String apiId;

    private String requestId;

    private String resourceId;

    private String resourcePath;

    private String sourceId;

    private String userAgent;

    private String apiKey;

    private String stage;


}
