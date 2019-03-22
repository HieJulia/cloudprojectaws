package com.project.aws.utils;



public class StringUtils {


    private StringUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static boolean checkNullOrEmpty(String string){
        return string != null && !string.isEmpty();
    }

}





