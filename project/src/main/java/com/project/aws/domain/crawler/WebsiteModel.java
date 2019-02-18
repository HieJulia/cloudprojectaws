package com.project.aws.domain.crawler;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WebsiteModel {
    // Website Model


    private String url;

    private String title;

    private String body;

    private Set<WebsiteModel> nodes = new HashSet<>();

    @JsonIgnore
    private WebsiteModel parent;

}
