package com.lessons.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
public class ReportsService {

    private static final Logger logger = LoggerFactory.getLogger(ReportsService.class);

    @Resource
    private DataSource dataSource;

    public Map<String, Object> getReport(int reportId){

        logger.debug("getReport() called");

        String sql = "Select * from reports where id = ?";
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        Map<String, Object> result = jt.queryForMap(sql, reportId);
        return result;
    }

    public List<Map<String, Object>> getAllReports(){

        logger.debug( "getAllReports() called");

        String sql = "Select id, description, created_date from reports";
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        List<Map<String, Object>> result = jt.queryForList(sql);
        return result;
    }
}
