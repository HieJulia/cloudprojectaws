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

    public String sendEmail(EmailDTO emailDTO) {
        private static final String REGION = "US_EAST_1";

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
                                            .withCharset(Constants.Email.CHARSET).withData(emailDTO.getHtmlBody()))
                                    .withText(new Content()
                                            .withCharset(Constants.Email.CHARSET).withData(emailDTO.getTextBody())))
                            .withSubject(new Content()
                                    .withCharset(Constants.Email.CHARSET).withData(emailDTO.getSubject())))
                    .withSource(emailDTO.getFrom());


            // Validation - tro dua cua no-
            if (emailDTO.getConfigSet() != null){
                ((SendEmailRequest) client).withConfigurationSetName(emailDTO.getConfigSet());
            }

            client.sendEmail(request);

            return Constants.Email.EMAIL_SENT_MESSAGE;

        } catch (Exception ex) {
            throw new EmailException(ex.getMessage());
        }
    }
}