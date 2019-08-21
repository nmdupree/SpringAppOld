package com.lessons;

import com.lessons.services.IndicatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IndicatorServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(IndicatorServiceTest.class);

    @Resource
    private IndicatorService indicatorService;

    public IndicatorServiceTest(){
        logger.debug("IndicatorServiceTest constructor called");
    }

    @PostConstruct
    public void init(){
        logger.debug("IndicatorServiceTest post constructor called");
    }

    @Test
    public void testCase1(){
        logger.debug("testCase1() called");
        Integer indicatorCount = indicatorService.getIndicatorCount();

        assertTrue("Count did not contain expected value.", indicatorCount==6);

        logger.debug("testCase1() complete");
    }


}
