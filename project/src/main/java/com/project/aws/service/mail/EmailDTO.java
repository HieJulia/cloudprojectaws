package com.project.aws.service.mail;

import lombok.Data;

@Data
public class EmailDTO {


    private String id;

    private String body;

    private String header;

    private String to;

    private String from;



}
