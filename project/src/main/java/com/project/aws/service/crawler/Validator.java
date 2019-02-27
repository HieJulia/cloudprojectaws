package com.project.aws.service.crawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import net.minidev.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Validator {


    private final static String NYSESymbol = "https://www.nyse.com/quote/XNYS:";
    private final static String NasdaqSymbol = "www.nasdaq.com/symbol/";
    static Properties stock = new Properties();


    public static void setSymbolProperties() throws IOException
    {
        InputStream is = new FileInputStream("stockSymbol.properties");
        stock.load(is);
    }


    /**
     * @param title
     * @return
     */
    public static String getTitle(String title) {
        title = title.substring(0, title.indexOf(' '));
        title = title.replaceAll("[,'-.]"," ").trim().replaceAll(" +", " ");
        return title;
    }

    // Get the basic stock information of the page
    public static JSONObject getStockInfo(Document Doc) {
        JSONObject stock = new JSONObject();
        String symbol  = null;
        Elements links = Doc.select("a[href]");

        for(int i=0 ;i<links.size();i++)
        {

            if(links.get(i).text().equals("NYSE")||links.get(i).text().equals("NASDAQ") )
            {
                symbol  = links.get(i+1).text();

                if(validateSymbol(getTitle(Doc.title()), symbol))
                {
                    stock.put("StockExchange", links.get(i).text());
                    stock.put("Symbol", symbol);
                }
                return stock;
            }


        }
        return null;
    }

    // Validate the symbol on the page to be a geniune Stock symbol related to the webpage

    /**
     * Validate the symbol on the page to be a geniune stock symbol related to the webpage
     * @param title
     * @param symbol
     * @return
     */
    public static boolean validateSymbol(String title, String symbol) {

        // Validate the symbol
        String StockName = null;


        StockName = stock.getProperty(symbol);

        if(StockName==null)
            return false;


        if(title.toLowerCase().contains(StockName.toLowerCase())||StockName.toLowerCase().contains(title.toLowerCase()))
            return true;
        else
            return false;
    }


    /**
     * Validate the link to be a Nasdaq or NYSE page

     * @param Doc
     * @return
     */
    public static boolean validateLink(Document Doc) {
        if(Doc.toString().contains(NYSESymbol)||Doc.toString().contains(NasdaqSymbol))
            return true;
        else
            return false;
    }


}





