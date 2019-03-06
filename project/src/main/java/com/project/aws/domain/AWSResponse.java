package com.project.aws.domain;


import com.amazonaws.http.HttpResponse;
import com.amazonaws.util.IOUtils;

import java.io.IOException;

public class AWSResponse {

    // HttpResponse - body
    private final HttpResponse httpResponse;
    private final String body;

    public AWSResponse(HttpResponse httpResponse) throws IOException {
        this.httpResponse = httpResponse;
        this.body = IOUtils.toString(httpResponse.getContent());
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public String getBody() {
        return body;
    }
}



