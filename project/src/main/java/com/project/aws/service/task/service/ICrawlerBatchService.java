package com.project.aws.service.task.service;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import com.project.aws.domain.crawler.WebsiteModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.net.URI;
import java.util.Map;




public interface ICrawlerBatchService {

    // Batch Service


    // Get websites by URL
    Flux<WebsiteModel> getWebsites(WebsiteModel seedWebsite, WebsiteModel parentWebsite, URI currentUrl, Integer depth);





}
