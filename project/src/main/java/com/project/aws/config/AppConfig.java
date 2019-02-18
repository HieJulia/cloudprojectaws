package com.project.aws.config;


import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import com.project.aws.domain.crawler.Profiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.JMSException;
import javax.jms.Session;


@EnableJms
@Configuration
@Profile(Profiles.INTEGRATED_ENVIRONMENT)
public class AppConfig {

    // AWS credential value
    @Value("${aws.credentials.provider}")
    private String CREDENTIALS_PROVIDER;


    // Config dynamodb - async

    @Bean
    public AmazonDynamoDBAsync getDynamoDBClient() {

        return AmazonDynamoDBAsyncClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider(CREDENTIALS_PROVIDER))
                .withRegion(Regions.AP_SOUTHEAST_2)
                .build();

        // credential  - region

    }


    @Bean
    public AmazonSQSAsync getSQSClient() {

        return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider(CREDENTIALS_PROVIDER))
                .withRegion(Regions.AP_SOUTHEAST_2)
                .build();

    }

    @Bean
    public SQSConnectionFactory sqsConnectionFactory() throws JMSException {

        return new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.standard()
                        .withRegion(Regions.AP_SOUTHEAST_2)
                        .withCredentials(new ProfileCredentialsProvider(CREDENTIALS_PROVIDER))
        );
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws JMSException {

        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsConnectionFactory());
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("3-10");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }


    // JMS template
    @Bean
    public JmsTemplate defaultJmsTemplate() throws JMSException {

        return new JmsTemplate(sqsConnectionFactory());
    }
}
