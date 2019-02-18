package com.project.aws.service.task;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URISyntaxException;


public interface ICrawlerBatchTask {

    // Get deep crawling


    // Get deep crawling task
    void getDeepCrawling(String requestJSON) throws URISyntaxException, JsonProcessingException;
}




//batch task : crawling task

