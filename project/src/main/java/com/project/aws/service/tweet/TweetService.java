package com.project.aws.service.tweet;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
/**
 * Tweet Service
 */
public class TweetService implements
        RequestHandler<S3Event, String>{
    // ES endpoint
    private static String ELASTICSEARCH_ENDPOINT = "https://search-codecamp-xqhqiobgmqleqsdpxmt2eh25aq.us-west-2.es.amazonaws.com";
    private static String ELASTICSEARCH_INDEX_NAME = "tweet_onlineshopping";
    private static String ELASTICSEARCH_INDEX_TYPE = "tweet";




    // Object -> String
    private String s3ObjectToString(final S3Object s3Object) throws IOException {
        StringWriter writer = new StringWriter();
        try (InputStream is = s3Object.getObjectContent()) {
            IOUtils.copy(is, writer, StandardCharsets.UTF_8);
            return writer.toString();
        }
    }


    // Handle request
    public void handleRequest(S3Event s3event, Context context) {
        try {
            // S3 Event - Context
            // Get record from S3
            final S3EventNotification.S3EventNotificationRecord record = s3event.getRecords().get(0);

            String srcBucket = record.getS3().getBucket().getName();

            String srcKey = record.getS3().getObject().getKey()
                    .replace('+', ' ');
            srcKey = URLDecoder.decode(srcKey, StandardCharsets.UTF_8.toString());


            // Download the data from S3 = stream with AWS client
            final AmazonS3 s3Client = new AmazonS3Client();
            // Get object from bucket - with key
            final S3Object s3Object = s3Client.getObject(new GetObjectRequest(
                    srcBucket, srcKey));

            String objectData = s3ObjectToString(s3Object);


            // Construct a Jest clietn - config
            final JestClientFactory factory = new JestClientFactory();

            factory.setHttpClientConfig(new HttpClientConfig
                    .Builder(ELASTICSEARCH_ENDPOINT)
                    .multiThreaded(false)
                    .build());
            // Build index
            final JestClient client = factory.getObject();

            final Index index = new Index.Builder(objectData)
                    .index(ELASTICSEARCH_INDEX_NAME)
                    .type(ELASTICSEARCH_INDEX_TYPE).build();
            client.execute(index);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
