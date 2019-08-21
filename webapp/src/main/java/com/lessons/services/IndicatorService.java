package com.lessons.services;

import com.lessons.models.IndicatorCountDTO;
import com.lessons.models.IndicatorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class IndicatorService {

    private static final Logger logger = LoggerFactory.getLogger(IndicatorService.class);

    @Resource
    private DataSource dataSource;

    public IndicatorService(){ logger.debug("IndicatorService Constructor called.");}

    @PostConstruct
    public void indicatorServicePostConstructor(){
        logger.debug("IndicatorService post constructor called.");
    }

    public Integer getIndicatorCount(){
        String sql = "SELECT COUNT(*) FROM indicators";
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);

        return jt.queryForObject(sql, Integer.class);
    }

    public IndicatorCountDTO getIndicatorCountRS(){

        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        String sql = "SELECT COUNT(*) AS ind_count FROM indicators";

        SqlRowSet rs = jt.queryForRowSet(sql);
        Integer count = 0;
        if (rs.next()){
            count = rs.getInt("ind_count");
        }
        IndicatorCountDTO ic = new IndicatorCountDTO(count);

        return ic;
    }

    public List<IndicatorDTO> getIndicatorList(){

        // Row mapper can convert camel- and snake-case, and ignore case, otherwise column names and
        // DTO variable names should be identical
        BeanPropertyRowMapper rowMapper = new BeanPropertyRowMapper(IndicatorDTO.class);
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        String sql = "SELECT id, value, created_date, created_by FROM indicators";
        List<IndicatorDTO> indicatorList = jt.query(sql, rowMapper);

        return indicatorList;
    }

}
