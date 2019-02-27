package com.project.aws.service.mail;



import com.amazonaws.regions.Regions;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import sun.tools.java.Constants;

public class Email {

    private final String REGION = "US_EAST_1";


    private final String CHARSET = "utf-8";




    public String sendEmail(EmailDTO emailDTO) {



        try {

            // Init Amazon Mail Service
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            .withRegion(Regions.DEFAULT_REGION).build();

            // Send email request
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            // destination
                            new Destination().withToAddresses(emailDTO.getTo()))
                    .withMessage(new Message()
                            // message - body
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset(CHARSET).withData(emailDTO.getBody()))
                                    .withText(new Content()
                                            .withCharset(CHARSET).withData(emailDTO.getBody())))
                            .withSubject(new Content()
                                    .withCharset(CHARSET).withData(emailDTO.getFrom())))
                    .withSource(emailDTO.getFrom());

            // Validation
            if (emailDTO.getBody()!= null){

                ((SendEmailRequest) client).withConfigurationSetName(emailDTO.getBody());
                // Thay vui vui

            }


            client.sendEmail(request);

            return Constants.Email.EMAIL_SENT_MESSAGE;

        } catch (Exception ex) {

            throw new EmailException(ex.getMessage());

        }
    }
}


