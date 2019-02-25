package com.project.aws.apiGateway;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Data
public class ApiGatewayResponse {

    private final int statusCode;

    private final String description;

    private final String body;

    private final Map<String, String> headers;

    private final boolean isBase64Encoded;
}
