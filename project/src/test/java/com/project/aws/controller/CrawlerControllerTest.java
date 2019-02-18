package com.project.aws.controller;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;

import com.project.aws.domain.crawler.Profiles;
import com.project.aws.service.crawler.ICrawlerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE) // Spring boot test -
@ActiveProfiles(value = {Profiles.MOCK_ENVIRONMENT}) // active profile : mock env
public class CrawlerControllerTest {


    @Autowired
    private CrawlerController crawlerController;

    //
    @MockBean
    private ICrawlerService crawlerService;

    private WebTestClient webTestClient;

    @MockBean
    private AmazonDynamoDBAsync amazonDynamoDBAsync;

    @Before
    public void setUp() throws Exception {

        webTestClient = WebTestClient.bindToController(crawlerController).build();
    }


    // Test
    @Test
    public void getDeepCrawlingReactive() throws Exception {

        String url = "http://example.com";


        given(this.crawlerService.getWebsites(url))
                .willReturn(this.getWebsites(url)); // return this website

        webTestClient.get().uri("/api/crawler/deep-crawling/reactive?url=" + url)
                .exchange()
                // expect staus -
                .expectStatus().isOk()
                .expectBody()
                // expect body -
                .consumeWith(System.out::println)
                // json path
                .jsonPath("$.url").isEqualTo(url);


    }



    public Mono<Map<String, Object>> getWebsites(String url) {

        return Mono.just(getMockedWebsites(url));
    }

    private Map<String, Object> getMockedWebsites(String url) {

        Map<String, Object> response = new HashMap<>();
        response.put("url", url);
        response.put("status", "NEW");

        return response;
    }


}
