package com.project.aws.service.crawler;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface ICrawlerService {


    // Get websites
    Mono<Map<String, Object>> getWebsites(String url);

}
