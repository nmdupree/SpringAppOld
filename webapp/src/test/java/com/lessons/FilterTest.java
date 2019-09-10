package com.lessons;


import com.lessons.filter.FilterParams;
import com.lessons.filter.FilterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilterTest {
    private static final Logger logger = LoggerFactory.getLogger(FilterTest.class);

    @Resource
    FilterService filterService;

    @Test
    public void filterTestCase1(){
        logger.debug("filterTestCase1() called");

        List<String> testFilterList = new ArrayList<>();
        testFilterList.add("ID~EQUALS~5");
        FilterParams filterParams = filterService.getFilterParamsForFilters(testFilterList);

        assertTrue("The output filterparam map did not have the correct size.",
                (filterParams.getSqlParams().size() == 1));

        assertTrue("The expected SQL clause was incorrect.",
                (filterParams.getSqlWhereClause().equalsIgnoreCase("ID = :bind0::Integer")));
    }

    @Test
    public void filterTestCase2(){
        logger.debug("filterTestCase1() called");

        List<String> testFilterList = new ArrayList<>();
        testFilterList.add("ID~IN~5~10~15");
        FilterParams filterParams = filterService.getFilterParamsForFilters(testFilterList);

        assertTrue("The output filterparam map did not have the correct size.",
                (filterParams.getSqlParams().size() == 1));

        assertTrue("The expected SQL clause was incorrect.",
                (filterParams.getSqlWhereClause().equalsIgnoreCase("ID IN :bind0")));
    }
}
