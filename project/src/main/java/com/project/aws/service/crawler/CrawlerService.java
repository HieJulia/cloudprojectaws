package com.project.aws.service.crawler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.document.internal.InternalUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;


import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;



@Service("crawlerService")
public class CrawlerService implements ICrawlerService {

    private static final Logger LOG = LoggerFactory.getLogger(CrawlerService.class);

    private AmazonDynamoDBAsync dynamoDBAsync;

    private AmazonSQSAsync sqs;

    @Value("${aws.sqs.endpoint}")
    private String SQS_ENDPOINT;

    public CrawlerService(AmazonDynamoDBAsync dynamoDBAsync, AmazonSQSAsync sqs) {

        this.dynamoDBAsync = dynamoDBAsync;
        this.sqs = sqs;
    }

    // Get website
    @Override
    public Mono<Map<String, Object>> getWebsites(String url) {
        // url

        return Mono.from(this.getItemResultMono(url))
                .flatMap(
                        (result) -> {
                            if (result.getItem() != null) return Mono.just(result.getItem());
                            return this.putItemResultMono(url);
                        })
                .doOnError((throwable -> LOG.error(Utils.error.failed_get_website, url)))

                .map(InternalUtils::toSimpleMapValue);
    }


    private Mono<GetItemResult> getItemResultMono(String url) {
        // Get item result mono - url

        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.setTableName(Utils.table.websites);
        HashMap<String, AttributeValue> key = new HashMap<>();
        key.put(Utils.params.url, new AttributeValue(url));
        getItemRequest.setKey(key);

        return Mono.fromFuture(
                Utils.makeCompletableFuture(
                        dynamoDBAsync.getItemAsync(getItemRequest)))
                .doOnError((throwable -> LOG.error(Utils.error.failed_dynamo_get, url)));
    }

    // Put item result mono
    private Mono<Map<String, AttributeValue>> putItemResultMono(String url) {

        PutItemRequest putItemRequest = new PutItemRequest();
        putItemRequest.setTableName(Utils.table.websites);
        HashMap<String, AttributeValue> newWebsite = new HashMap<>();
        newWebsite.put(Utils.params.url, new AttributeValue(url));
        newWebsite.put(Utils.params.status, new AttributeValue(EStatus.NEW.name()));
        putItemRequest.setItem(newWebsite);

        return Mono.fromFuture(
                Utils.makeCompletableFuture(
                        dynamoDBAsync.putItemAsync(putItemRequest)))
                .doOnError((throwable -> LOG.error(Utils.error.failed_dynamo_put, url)))
                .flatMap((created) -> this.sendMessage(url))
                .map(((result) -> putItemRequest.getItem()));
    }


    // Send message function
    private Mono<SendMessageResult> sendMessage(String body) {
        // SQS endpoint - body
        SendMessageRequest sendMessageRequest = new SendMessageRequest(
                SQS_ENDPOINT,
                body
        );

        return Mono.fromFuture(
                Utils.makeCompletableFuture(
                        // sqs.sendMessageAsync
                        sqs.sendMessageAsync(sendMessageRequest)))

                .doOnError((throwable -> LOG.error(Utils.error.failed_sqs, body)));
    }


}
