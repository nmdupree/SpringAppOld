package com.lessons.controllers;

import com.lessons.models.ShortReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller("com.lessons.controllers.WelcomeController")
public class WelcomeController {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    /**********************************************************************
     * showDefaultPage()
     *
     * If the user connects to http://localhost:8080/app1 or http://localhost:8080/app1/page/...
     * Then take the user to the main landing page
     *
     * This is needed to ensure that page refreshes keep the user in the single-page-app
     ***********************************************************************/
    @RequestMapping(value = {"/", "/page/**"}, method = RequestMethod.GET, produces = "text/plain")
    public String showDefaultPage()
    {
        logger.debug("showDefaultPage() started");

        // Forward the user to the main page
        return "forward:/app.html";
    }

    @RequestMapping( value = "/api/bad", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> badRequest(){
        logger.debug("badRequest() called");

        int i = 1;
        if (i==1) {
            throw new RuntimeException("A critical error has occured. Which means you probably suck.");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("");

    }
}
