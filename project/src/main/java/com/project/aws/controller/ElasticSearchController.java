package com.project.aws.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.project.aws.domain.AWSResponse;
import com.project.aws.domain.Product;
import com.project.aws.domain.query.StockQuery;
import com.project.aws.service.search.ElasticSearchService;
import com.project.aws.utils.constants.ElasticSearchConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/v1/api/search")
public class ElasticSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchController.class);

    @Autowired
    private ElasticSearchService elasticSearchService;


    /**
     * Get a set of products that match query criteria
     */
    @PostMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> getFromElasticSearch(@RequestBody final StockQuery stockQuery) {

        return ResponseEntity.status(HttpStatus.OK).body(

                elasticSearchService.getMovies(ElasticSearchConstants.PRODUCTS_INDEX, 0, 100, null, stockQuery));
    }

    /**
     * Fuzzy search the Products index with a partial word, or one word in a sentence
     *
     */
    @PostMapping(value = "/fuzzySearch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> getFromElasticSearchFuzzySearch(@RequestBody final StockQuery stockQuery) {
        return ResponseEntity.status(HttpStatus.OK).body(

                elasticSearchService.getMoviesFuzzySearch(ElasticSearchConstants.PRODUCTS_INDEX, 0, 100, null, stockQuery));

    }

    /**
     * Create a new Product stock in ES
     *
     */
    @PostMapping(value = "/create", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public ResponseEntity<String> createElasticSearchObject(@RequestBody final Product product) {

        String title = null;
        try {

            title = elasticSearchService.createNewMovie(product);
            if (title != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Successfully created " + title);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to create Movie.", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create  " + product.getName());
    }

    /**
     * Update a Product object in ElasticSearch
     *
     */
    @PutMapping(value = "/update", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public ResponseEntity<String> updateElasticSearchObject(@RequestBody final Product product,
                                                            @RequestParam(value = "id", required = true) final Long id) {




        String title = null;
        try {
            title = elasticSearchService.updateMovie(id, movie);
            if (title != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Successfully updated " + title);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to update Movie.", e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update  " + movie.getTitle());
    }

    /**
     * Delete a Product stock object in ElasticSearch
     */
    @DeleteMapping(value = "/product/stock/delete", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public ResponseEntity<String> deleteFromElasticSearch(@RequestParam("index") final String index,
                                                          @RequestParam("type") final String type,
                                                          @RequestParam("id") final String id) {


        AWSResponse response = elasticSearchService.deleteDocument(index, type, id);

        if (response != null && response.getHttpResponse().getStatusCode() == 200) {


            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted movie with ID of " + id);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting ElasticSearch document");
        }
    }

    /**
     * Get statistics about an ElasticSearch Index
     */
    @GetMapping(value = "/statistics", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> indexStatistics(@RequestParam("index") final String index) {

        String response = elasticSearchService.getIndexStatistics(index);

        if (response != null) {

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } else {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching statistics for index");

        }
    }
}