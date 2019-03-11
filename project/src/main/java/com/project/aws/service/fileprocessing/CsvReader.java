package com.project.aws.service.fileprocessing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CsvReader {


    /**
     * Read as String
     *
     *
     * @param url
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     *
     *
     */
    public static ArrayList readAsStrings(String url) throws FileNotFoundException, IOException {
        /**returns all of the data in a file as Strings given the File object*/
        ArrayList data = new ArrayList();
        InputStream ips= CsvReader.class.getResourceAsStream(url);
        InputStreamReader ipsr = new InputStreamReader(ips);
        BufferedReader reader = new BufferedReader(ipsr);
        String nextLine = reader.readLine();
        while (nextLine != null) {
            data.add(nextLine);
            nextLine = reader.readLine();
        }

        closeReader(url);

        return data;
    }


    /**
     * Extract from commas
     *
     * @param dataLine
     * @return
     */

    public static ArrayList extractFromCommas(String dataLine) {
        //Gives back the data that is found between commas in a String
        ArrayList data = new ArrayList();
        String theString = "";
        for (int i = 0; i < dataLine.length(); i++) {

            if (dataLine.charAt(i) == ',') {
                if (i == 0) {
                    //do nothing
                } else {
                    data.add(theString);
                    theString = "";
                }
            } else {
                theString = theString + dataLine.charAt(i);
            }
        }
        if (!theString.equalsIgnoreCase(""))
        {
            data.add(theString);
        }
        return data;
    }


    /**
     Close Reader stream

     * @param url
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void closeReader (String url) throws FileNotFoundException, IOException  {
        InputStream ips= CsvReader.class.getResourceAsStream(url);
        InputStreamReader ipsr = new InputStreamReader(ips);
        BufferedReader reader = new BufferedReader(ipsr);
        reader.close();

    }
}
