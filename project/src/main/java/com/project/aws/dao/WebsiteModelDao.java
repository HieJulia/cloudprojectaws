package com.project.aws.dao;

import com.project.aws.domain.crawler.WebsiteModel;

import java.util.List;


public interface WebsiteModelDao {


    // Get by url

    WebsiteModel getByUrl(String url);

    // List all
    List<WebsiteModel> getAll();


    // Create one
    void create(WebsiteModel websiteModel);




    // Delete one
    void deleteOne(WebsiteModel websiteModel);



    // Delete all






    //id, url, title, body



}
