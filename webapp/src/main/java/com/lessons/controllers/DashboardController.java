package com.lessons.controllers;


import com.lessons.models.ReportDTO;
import com.lessons.models.ShortReportDTO;
import com.lessons.services.DashboardDao;
import com.lessons.services.ReportsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller("com.lessons.controllers.DashboardController")
public class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Resource
    private DashboardDao dashboardDao;

    @Resource
    private ReportsService reportsService;

    public DashboardController(){
        logger.debug("Constructor called");
    }

    @PostConstruct
    public void dashboardControllerPostConstruct(){
        logger.debug("DashboardController post constructor called");
    }


    /*************************************************************************
     * getDateTime()
     * @return JSON string that holds the date/time
     *************************************************************************/
    @RequestMapping(value = "/app1/api/dashboard/time", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getDateTime() {
        logger.debug("getDashboardDetails() started.");

        // Get the date/time from the database
        String sDateTime = dashboardDao.getDatabaseTime();

        // Return the date/time string as plain-text
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.TEXT_PLAIN)
                .body(sDateTime);
    }

    @RequestMapping(value = "/api/nextval", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getNextNumberInSeq(){
        logger.debug("getNextNumberInSeq() started");

        int nextVal = dashboardDao.getNextVal();
        String result = "" + nextVal;

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.TEXT_PLAIN)
                .body(result);
    }

    @RequestMapping(value = "/api/dashboard/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> addNewRecord(@RequestParam(name="description") String description){
        logger.debug("addNewRecord() started");

        dashboardDao.addNewRecord(description);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.TEXT_PLAIN)
                .body("");
    }

    @RequestMapping(value = "/api/reports", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getOneReport(@RequestParam(name="id") Integer reportId){
        logger.debug("getOneReport() started; reportID = {}", reportId);

        Map<String, Object> resultingReport = reportsService.getReport(reportId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultingReport);
    }

    @RequestMapping(value = "/api/reports/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getOnePrettyReport(@PathVariable(name="id") Integer reportId){
        logger.debug("getOnePrettyReport() started; reportID = {}", reportId);

        Map<String, Object> resultingReport = reportsService.getReport(reportId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultingReport);
    }

    @RequestMapping(value = "/api/reports/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllReports(){
        logger.debug("getAllReports() started");

        List<Map<String, Object>> resultingReport = reportsService.getAllReports();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultingReport);
    }

    @RequestMapping( value = "/api/reports/params", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> allParams(@RequestParam Map<String, Object> inputMap){
        logger.debug("allParams() called");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(inputMap);

    }

    @RequestMapping( value = "/api/reports/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> all(@RequestBody ReportDTO reportDTO){
        logger.debug("add() called");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reportDTO);

    }

    @RequestMapping(value = "/api/reports/review", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> review(@RequestParam(name="id") Integer reportId, @RequestParam(name="reviewed") Boolean reportReviewed){
        logger.debug("review() called");

        if (!dashboardDao.recordExists(reportId)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Record not found, dummy.");
        }

        dashboardDao.updateReviewed(reportId, reportReviewed);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("");
    }

    @RequestMapping( value = "/api/reports/short", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> shortReport(){
        logger.debug("shortReport() called");

        List<ShortReportDTO> dtoList = reportsService.getShortReport2();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dtoList);

    }
}
