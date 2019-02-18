package com.project.aws.domain.crawler;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@DynamoDBTable(tableName = "websitemodel")
@JsonIgnoreProperties(ignoreUnknown=true)
public class WebsiteModel implements Serializable {
    // Website Model

    private String id;

    private String url;

    private String title;

    private String body;

    private Set<WebsiteModel> nodes = new HashSet<>();

    @JsonIgnore
    private WebsiteModel parent;






}
