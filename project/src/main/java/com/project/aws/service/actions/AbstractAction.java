package com.project.aws.service.actions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractAction implements Action{

    /**
     * Returns an initialized Gson object with the default configuration
     *
     *
     * Init Gson object
     * @return An initialized Gson object
     */
    protected Gson getGson() {
        return new GsonBuilder()
                //.enableComplexMapKeySerialization()
                //.serializeNulls()
                //.setDateFormat(DateFormat.LONG)
                //.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
    }
}
