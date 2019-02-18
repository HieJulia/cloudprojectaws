package com.project.aws.controller;


import com.project.aws.service.crawler.ICrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/crawler")
public class CrawlerController {


    @Autowired
    private ICrawlerService crawlerService;


    // Get URL
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Object>> getDeepCrawlingReactive(
            @Valid @RequestParam(value = "url") String url
    ) {

        return crawlerService.getWebsites(url);
    }





}