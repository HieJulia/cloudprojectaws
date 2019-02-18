package com.project.aws.service.task.service;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.internal.InternalUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.aws.config.Utils;
import com.project.aws.domain.crawler.WebsiteModel;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service("crawlerBatchService")
public class CrawlerBatchService implements ICrawlerBatchService {


    private static final Logger LOG = LoggerFactory.getLogger(CrawlerBatchService.class);

    private ConcurrentHashMap<String, WebsiteModel> urls;

    @Value("${max.depth}")
    private Integer MAX_DEPTH;



    // GET WEBSITES
    @Override
    public Flux<WebsiteModel> getWebsites(
            WebsiteModel seedWebsite,
            WebsiteModel parentWebsite,
            URI currentUrl,
            Integer depth
    ) {

        if (depth == 0) urls = new ConcurrentHashMap<>();

        if (depth > MAX_DEPTH) return Flux.just(seedWebsite);

        return Flux.merge(
                Flux.empty(),
                Flux.from(this.getBody(currentUrl))
                        .flatMap((body) -> getWebsiteModel(seedWebsite, parentWebsite, currentUrl.toString(), body, depth))
                        .flatMap((website) -> getParsedUrl(website, seedWebsite.getUrl(), depth))
                        .flatMap((website) -> {
                            LOG.info(Utils.success.log_recursion, depth, seedWebsite.getUrl(), website.getUrl());
                            try
                            {
                                urls.put(website.getUrl(), website);
                                return getWebsites(seedWebsite, website.getParent(), new URI(website.getUrl()), depth + 1);

                            }
                            catch (URISyntaxException e)
                            {
                                LOG.error(e.getMessage());
                                return Flux.empty();
                            }
                        })
        );
    }


    /**
     * Get body from url
     *
     * @param url
     * @return
     */
    private Mono<String> getBody(URI url) {

        return WebClient.create().get()
                .uri(url)
                .accept(MediaType.TEXT_HTML) // html
                .exchange()
                .filter(clientResponse -> clientResponse.statusCode() == HttpStatus.OK)
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                // Error : failed - get - website - url
                .doOnError(throwable -> LOG.error(Utils.error.failed_get_website, url))
                // Error resume
                .onErrorResume(throwable -> Mono.never());
    }


    /**
     * Get website model
     *
     * @param seedWebsite
     * @param parentWebsite
     * @param currentUrl
     * @param content
     * @param depth
     * @return
     */
    private Flux<WebsiteModel> getWebsiteModel(WebsiteModel seedWebsite, WebsiteModel parentWebsite, String currentUrl, String content, Integer depth) {

        return Mono.just(content)
                .flatMapIterable((body) -> {
                    Document document = Jsoup.parse(body, currentUrl);
                    Elements elements = document.select(Utils.document.links);

                    WebsiteModel currentWebsite = getCurrentWebsite(
                            seedWebsite,
                            parentWebsite,
                            currentUrl,
                            depth,
                            document
                    );

                    Set<WebsiteModel> nodes = elements.stream()
                            .map(
                                    (link) -> {
                                        WebsiteModel childWebsite = new WebsiteModel();
                                        String linkUrl = link.attr(Utils.document.link_url);
                                        childWebsite.setUrl(linkUrl);
                                        childWebsite.setParent(depth == 0 ? seedWebsite : currentWebsite);
                                        return childWebsite;
                                    })
                            .collect(Collectors.toSet());

                    currentWebsite.setNodes(nodes);

                    return nodes;

                });
    }


    // Tinh cach con nho nay y chang nhu con trai - no van co li tri de no code tiep
    private Mono<WebsiteModel> getParsedUrl(WebsiteModel currentWebsite, String seedUrl, Integer depth) {

        return Mono.just(currentWebsite)
                .flatMap((website) -> filter(seedUrl, website, depth))
                .map((website) -> {
                    website.setUrl(StringUtils.split(website.getUrl(), "?")[0]);
                    return website;
                })
                .map((website) -> {
                    website.setUrl(StringUtils.split(website.getUrl(), "#")[0]);
                    return website;
                })
                .filter(website -> !urls.containsKey(website.getUrl()));
    }

    private Mono<WebsiteModel> filter(String seedUrl, WebsiteModel currentWebsite, Integer depth) {

        return Mono.just(currentWebsite)
                .filter(website -> StringUtils.isNotEmpty(website.getUrl()) && (website.getUrl().startsWith(seedUrl) || depth == 0))
                .filter(website -> !StringUtils.startsWith(website.getUrl(), seedUrl + "/search"))
                .filter(website -> StringUtils.startsWith(website.getUrl(), "http"));
    }


    /**
     * Get current website
     *
     * @param seedWebsite
     * @param parentWebsite
     * @param currentUrl
     * @param depth
     * @param document
     * @return
     */
    private WebsiteModel getCurrentWebsite(
            WebsiteModel seedWebsite,
            WebsiteModel parentWebsite,
            String currentUrl,
            Integer depth,
            Document document
    ) {

        WebsiteModel currentWebsite = urls.get(currentUrl);

        if (currentWebsite == null)
        {
            currentWebsite = new WebsiteModel();
        }

        if (depth == 0)
        {
            seedWebsite.setTitle(document.title());
            seedWebsite.setUrl(currentUrl);
        }
        else
        {
            currentWebsite.setTitle(document.title());
            currentWebsite.setUrl(currentUrl);
            parentWebsite.getNodes().add(currentWebsite);
        }

        return currentWebsite;
    }
}