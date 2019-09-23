package com.lessons;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddTestRecords {
    private static final Logger logger = LoggerFactory.getLogger(AddTestRecords.class);

    @Resource
    private DataSource dataSource;


    @Test
    public void fakeTest(){
        logger.debug("fakeTest() called");
    }

    @Test
    public void insertFakeRecords(){
        logger.debug("insertFakeRecords() called");

        int totalRecordsToCreate = 5;

        for (int i = 1; i <= totalRecordsToCreate; i++){
            int reportId = getUniqueId();
            createReport(reportId);

            int totalIndicatorRecords = getRandomNumBetween(1,100);
            createIndicators(reportId, totalIndicatorRecords);

            if(i % 100 == 0){
                logger.debug("Processing report {} of {}", i, totalRecordsToCreate);
            }
        }

    }

    // RUNTIME: 13m 33s 202ms
    // RECORDS:
    @Test
    public void insertFakeRecordsML(){
        logger.debug("insertFakeRecordsML() called");

        int totalRecordsToCreate = 50000;

        for (int i = 1; i <= totalRecordsToCreate; i++){
            int reportId = getUniqueId();
            createReport(reportId);

            int totalIndicatorRecords = getRandomNumBetween(1,100);
            createIndicatorsML(reportId, totalIndicatorRecords);

            if(i % 100 == 0){
                logger.debug("Processing report {} of {}", i, totalRecordsToCreate);
            }
        }

    }

    private void createReport(int reportId){
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        String displayName = "record_name_" + reportId + ".txt";
        boolean isCustomReport = getRandomBoolean();
        String sql = "INSERT INTO reports(id, display_name, is_custom_report) VALUES(?, ?, ?)";
        jt.update(sql, reportId, displayName, isCustomReport);
    }

    private void createIndicatorsML(int reportId, int totalIndicators){
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        String indSql = "INSERT INTO indicators (id, value, ind_type) VALUES ";
        String lriSql = "INSERT INTO link_reports_indicators(id, report, indicator) VALUES ";
        String audSql = "INSERT INTO indicators_aud(id, value, ind_type, rev, rev_type, timestamp, username) VALUES ";


        for (int i = 1; i <= totalIndicators; i++){

            // Create and append Indicator Record
            int indicatorID = getUniqueId();
            String ipAddress = getRandomIp();
            int indicatorType = getRandomNumBetween(1, 5);
            indSql = indSql + "(" + indicatorID + ",'" + ipAddress + "', " + indicatorType + "),";

            // Create and insert Link Reports Indicators
            int lriId = getUniqueId();
            lriSql = lriSql + "(" + lriId + "," + reportId + "," + indicatorID + "),";

            // Create and insert Audit Records
            int rev = getUniqueId();
            audSql = audSql + "(" + indicatorID + ",'" + ipAddress + "', " + indicatorType + ", " + rev + ", 0, now(), 'ndupree' ),";

        }
        // Strip trailing commas
        indSql = StringUtils.substring(indSql,0, indSql.length() - 1);
        lriSql = StringUtils.substring(lriSql,0, lriSql.length() - 1);
        audSql = StringUtils.substring(audSql,0, audSql.length() - 1);

        //
        jt.update(indSql);
        jt.update(lriSql);
        jt.update(audSql);

    }

    private void createIndicators(int reportId, int totalIndicators){
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);

        for (int i = 1; i <= totalIndicators; i++){

            // Create and insert Indicator Record
            int indicatorID = getUniqueId();
            String ipAddress = getRandomIp();
            int indicatorType = getRandomNumBetween(1, 5);
            String indSql = "INSERT INTO indicators (id, value, ind_type) VALUES(?, ?, ?)";
            jt.update(indSql, indicatorID, ipAddress, indicatorType);

            // Create and insert Link Reports Indicators
            int lriId = getUniqueId();
            String lriSql = "INSERT INTO link_reports_indicators(id, report, indicator) VALUES(?, ?, ?)";
            jt.update(lriSql, lriId, indicatorID, reportId);

            // Create and insert Audit Records
            String audSql = "INSERT INTO indicators_aud(id, value, ind_type, rev, rev_type, timestamp, username) VALUES(?, ?, ?, ?, ?, now(), 'ndupree')";
            int rev = getUniqueId();
            jt.update(audSql, indicatorID, ipAddress, indicatorType, rev, 0);

        }
    }

    private int getRandomNumBetween(int min, int max){
        int randomNum = ThreadLocalRandom.current().nextInt(min, max +1);
        return randomNum;
    }

    private boolean getRandomBoolean(){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 2);
        return randomNum == 1;
    }

    private int getUniqueId(){
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        String sql = "SELECT nextval('seq_table_ids')";
        int nextVal = jt.queryForObject(sql, Integer.class);
        return nextVal;
    }

    private String getRandomIp(){
        int octet1 = getRandomNumBetween(1, 223);
        int octet2 = getRandomNumBetween(0, 255);
        int octet3 = getRandomNumBetween(0, 255);
        int octet4 = getRandomNumBetween(1, 255);
        String ipAddress = octet1 + "." + octet2 + "." + octet3 + "." + octet4;
        return ipAddress;
    }
}
