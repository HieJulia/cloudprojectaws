package com.project.aws.apiGateway;


import lombok.Data;

import java.util.Map;


@Data
public class Response {

    // Response : message - input

    private final String message;

    private final String description;

    private final Map<String, Object> input;


}