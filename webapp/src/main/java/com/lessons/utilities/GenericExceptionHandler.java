package com.lessons.utilities;

import io.undertow.servlet.attribute.ServletRequestAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GenericExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @Value("${development.mode}")
    private Boolean developmentMode;

    public GenericExceptionHandler(){
        logger.debug("GenericExceptionHandler constructor called.");
    }

    @PostConstruct
    public void genericExceptionHandlerPostConstruct(){
        logger.debug("GenericExceptionHandler post constructor called.");
    }

    /**
     * GenericExceptionHandler handles and logs errors from all REST Endpoints
     * @param exception
     * @return ResponseEntity object with information about the exception
     */
    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception exception){

        logger.error("You done fucked up, son.", exception);

        /*
            HttpServletRequest collects and supplies the URI of the erroneous request which can then be logged.
         */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null){
            logger.error("This request to " + request.getRequestURI() + "raised an exception.", exception);
        } else{
            logger.error("Exception raised: ", exception);
        }

        /*
            All @ExceptionHandlers should log the exception as an error, and differentiate between developer mode
            and end-user responses (detailed responses to end users pose security risk)
            See @Value developmentMode pulling from application.yaml
         */
        if (developmentMode){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(exception.getLocalizedMessage());
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Something went wrong. Please contact the system administrator.");
        }


    }

}
