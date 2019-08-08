package com.lessons.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

@Service
public class DashboardDao
{
    private static final Logger logger = LoggerFactory.getLogger(DashboardDao.class);

    @Resource
    private DataSource dataSource;

    public DashboardDao()
    {
        logger.debug("DashboardDao() Constructor called");
    }

    @PostConstruct
    public void dashboadDaoPostConstruct(){
        logger.debug("DashboardDao post constructor called");
    }


    public String getDatabaseTime()
    {
        logger.debug("getDatabaseTime() started.");

        // Run a SQL query to get the current date time
        String sql = "Select NOW()";
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        String sDateTime = jt.queryForObject(sql, String.class);
        logger.debug("Database Time is {}", sDateTime);

        return sDateTime;
    }

    public int getNextVal(){
        logger.debug("Called getNextVal");

        String sql = ("Select nextval('seq_table_ids')");
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        int nextVal = jt.queryForObject(sql, Integer.class);
        logger.debug("Next value is {}", nextVal);

        return nextVal;
    }

    public void addNewRecord(String desc){
        logger.debug("Called addNewRecord");

        String sql = "Insert into reports(id, description) values(nextval('seq_table_ids'), ?)";
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        jt.update(sql, desc);

        logger.debug("addNewRecord() completed");
    }
}
