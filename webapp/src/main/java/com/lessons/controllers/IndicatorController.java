package com.lessons.controllers;

import com.lessons.models.IndicatorCountDTO;
import com.lessons.models.IndicatorDTO;
import com.lessons.services.IndicatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.ws.Response;
import java.util.List;

@Controller("com.lessons.controllers.IndicatorController")
public class IndicatorController {

    private static final Logger logger = LoggerFactory.getLogger(IndicatorController.class);

    @Resource
    private IndicatorService indicatorService;

    public IndicatorController() {  logger.debug("IndicatorService Constructor called");    }

    @PostConstruct
    public void postConstructIndicatorController() {    logger.debug("IndicatorService PostConstruct called."); }

    @RequestMapping(value = "/api/indicator/count", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> totalIndicator(){
        logger.debug("totalIndicators() called");

        IndicatorCountDTO ic = indicatorService.getIndicatorCountRS();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ic);
    }

    @RequestMapping(value = "/api/indicator/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> indicatorDTO(){
        logger.debug("indicatorDTO() called");

        List<IndicatorDTO> indicatorList = indicatorService.getIndicatorList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(indicatorList);
    }
}
