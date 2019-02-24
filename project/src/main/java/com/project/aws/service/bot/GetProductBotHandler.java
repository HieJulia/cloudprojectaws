//package com.project.aws.service.bot;
//
//import com.project.aws.service.handler.AbstractLexRequestHandler;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.ai.chatbot.framework.request.LexRequest;
//import com.ai.chatbot.framework.response.LexResponse;
//import com.ai.chatbot.framework.response.Message;
//import com.ai.chatbot.framework.response.action.CloseDialogAction;
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
//import com.fasterxml.jackson.core.type.TypeReference;
//
//
//public class GetProductBotHandler extends AbstractLexRequestHandler implements RequestStreamHandler{
//
//    private Logger logger = LogManager.getLogger(getClass().getName());
//
//    // Process greetings from customer
//    private LexResponse processGreeting(LexRequest lexRequest,Map<String,String> sessionAttributes) {
//
//        // Pull attribute name from Request
//        String firstName = lexRequest.getCurrentIntent().getSlots().get("Hello shop");
//
//        // Store in session for future use
//        saveObjectIntoSession(sessionAttributes,"hello", firstName, new TypeReference<String>() {});
//
//        // Create Response
//        return new LexResponse(new CloseDialogAction("Fulfilled", new Message("PlainText","Hello "+ firstName + ", Hope your are doing great. What do you want to buy")),sessionAttributes);
//
//    }
//
//    // Handle greetings request
//    public void handleGreetingRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
//        // Convert request byte -> array
//        byte[] requestBytes = IOUtils.toByteArray(inputStream);
//        byte[] responseBytes = null;
//
//        /**
//         * Request != null
//         *  getSession
//         *  get intent name
//         *      hello - > process - request
//         */
//
//
//
//    }
//}
