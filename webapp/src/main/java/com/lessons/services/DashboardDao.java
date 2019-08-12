package com.lessons.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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

    public void updateReviewed(Integer id, Boolean reviewed){
        logger.debug("updateReviewd() started");

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("reviewed", reviewed);

        String sql = "update reports set reviewed = :reviewed where id = :id";
        NamedParameterJdbcTemplate np = new NamedParameterJdbcTemplate(dataSource);

        int count = np.update(sql, params);
        logger.debug("Updated {} record(s)", count);
    }

    public boolean recordExists(Integer recordId){

        String sql = "SELECT id FROM reports WHERE id = " + recordId;

        JdbcTemplate jt = new JdbcTemplate(dataSource);
        SqlRowSet rs = jt.queryForRowSet(sql);
        return rs.next();
    }
}
