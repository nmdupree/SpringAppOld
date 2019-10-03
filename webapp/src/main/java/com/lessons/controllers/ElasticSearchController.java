package com.lessons.controllers;

import com.lessons.models.SearchDTO;
import com.lessons.services.ElasticSearchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Controller("com.lessons.controllers.ElasticSearchController")
public class ElasticSearchController {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchController.class);

    @Resource
    private ElasticSearchService elasticSearchService;

    public ElasticSearchController() {
        logger.debug("ElasticSearchController Constructor called");
    }

    @PostConstruct
    public void postConstructElasticSearchController() {
        logger.debug("ElasticSearchController post constructor called");
    }

    @RequestMapping(value = "/api/search/old", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> searchAllOld() throws Exception{
        logger.debug("searchAll() called");

        String result = elasticSearchService.runSearchOld("");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @RequestMapping(value = "/api/search", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> searchAll(@RequestBody SearchDTO searchDTO) throws Exception{
        logger.debug("searchAll() called");

        if (searchDTO.getRawQuery() == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("raw_query string required but not provided. Get your life together.");
        }
        if (StringUtils.countMatches(searchDTO.getRawQuery(), "\"") % 2 != 0){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Unexpected \'\"\' in query string. Get your life together.");
        }
        if (searchDTO.getRawQuery().length() < 1){
            searchDTO.setRawQuery("*");
        }

        String result = elasticSearchService.runSearch(searchDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
