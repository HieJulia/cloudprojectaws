package com.project.aws.service.mail;

import lombok.Data;

@Data
public class SendEmailRequest {

    private String from;


    private String to;


    private String content;
}
