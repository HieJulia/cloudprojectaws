package com.project.aws.domain.response;


import lombok.Data;

@Data
public class Response {

    private Object data = new Object();

    private String code;

    private String message;
}
