package com.project.aws.service.handler;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class AbstractLexRequestHandler {
    private ObjectMapper objectMapper = new ObjectMapper();


    // Get from session
    // Save from session

    @SuppressWarnings("deprecation")
    protected void saveObjectIntoSession(Map<String,String> sessionAttributes, String key, Object value, TypeReference<?> typeReference) {
        String serializedObject;
        try {
            serializedObject = objectMapper.writerWithType(typeReference).writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException("Unable to serialize object to session", e);
        }
        sessionAttributes.put(key, serializedObject);
    }

    @SuppressWarnings("deprecation")
    protected <TYPE> TYPE getObjectFromSession(Map<String,String> sessionAttributes, String key, TypeReference<TYPE> typeReference) {
        String serializedObject = sessionAttributes.get(key);
        if (serializedObject == null) {
            return null;
        }
        TYPE object;
        try {
            object = objectMapper.reader(typeReference).readValue(serializedObject);
        } catch (Exception e) {
            throw new RuntimeException("Unable to deserialize object from session", e);
        }
        return object;
    }


}
