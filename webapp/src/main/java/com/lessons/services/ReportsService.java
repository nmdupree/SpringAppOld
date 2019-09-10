package com.lessons.services;

import com.lessons.filter.FilterParams;
import com.lessons.filter.FilterService;
import com.lessons.models.ReportStatsDTO;
import com.lessons.models.ShortReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReportsService {

    private static final Logger logger = LoggerFactory.getLogger(ReportsService.class);

    @Resource
    private DataSource dataSource;

    @Resource
    private FilterService filterService;

    @Value("${development.mode:false}")
    private Boolean developmentMode;

    @Value("${network}")
    private String network;

    public ReportsService(){
        logger.debug("Constructor called. The value of developmentMode is {}", developmentMode);
        logger.debug("Constructor called. The value of network is {}", network);
    }

    @PostConstruct
    public void reportsServicePostConstructor(){
        logger.debug("Post constructor called. The value of developmentMode is {}", developmentMode);
        logger.debug("Post constructor called. The value of network is {}", network);

        if (!(network.equalsIgnoreCase("nipr") || network.equalsIgnoreCase("sipr"))){
            throw new RuntimeException("Current network value is " + network + ".");
        }
    }

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

    // 2019.08.19 Old practice, inefficient; Uses queryForRowSet

    public List<ShortReportDTO> getShortReport(){

        logger.debug("getShortReport() called");

        List<ShortReportDTO> dtoList = new ArrayList<ShortReportDTO>();

        String sql = "SELECT * FROM reports";
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        SqlRowSet rs = jt.queryForRowSet(sql);
        while(rs.next()) {
            ShortReportDTO sr = new ShortReportDTO(rs.getInt("id"), rs.getString("description"), rs.getString("display_name"));
            dtoList.add(sr);
        }
        return dtoList;
    }

    // 2019.08.19 More modern practice; Use row mapper rather than while loop + queryForRowSet; Use DTO objects rather than list of maps

    public List<ShortReportDTO> getShortReport2(){

        BeanPropertyRowMapper rowMapper = new BeanPropertyRowMapper(ShortReportDTO.class);
        // Don't change this SQL - rowMapper will not fail, it will return nulls/bad data
        String sql = "SELECT id, description, display_name FROM reports";
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        List<ShortReportDTO> dtoList = jt.query(sql, rowMapper);

        return dtoList;
    }


    public List<ShortReportDTO> getFilteredReports(List<String> filters) {

        FilterParams fp = filterService.getFilterParamsForFilters(filters);
        String whereClause = fp.getSqlWhereClause();
        // Don't change this SQL - rowMapper will not fail, it will return nulls/bad data
        String sql = "SELECT id, description, display_name FROM reports";
        List<ShortReportDTO> dtoList;
        BeanPropertyRowMapper rowMapper = new BeanPropertyRowMapper(ShortReportDTO.class);

        if (!whereClause.isEmpty()){
            NamedParameterJdbcTemplate np = new NamedParameterJdbcTemplate(this.dataSource);
            sql = sql + " WHERE " + whereClause;
            dtoList = np.query(sql, fp.getSqlParams(), rowMapper);
        } else{
            JdbcTemplate jt = new JdbcTemplate(this.dataSource);
            dtoList = jt.query(sql, rowMapper);
        }

        return dtoList;
    }

    public List<ReportStatsDTO> getReportStats() {
        logger.debug("getReportStats() called");

        BeanPropertyRowMapper rowMapper = new BeanPropertyRowMapper(ReportStatsDTO.class);
        // Don't change this SQL - rowMapper will not fail, it will return nulls/bad data
        String sql = "SELECT lri.report, r.display_name, count(lri.indicator) AS indicator_count " +
                        "FROM link_reports_indicators lri " +
                        "JOIN reports r on lri.report = r.id " +
                        "GROUP BY lri.report, r.display_name " +
                        "ORDER BY indicator_count DESC " +
                        "LIMIT 5";
        JdbcTemplate jt = new JdbcTemplate(this.dataSource);
        List<ReportStatsDTO> dtoList = jt.query(sql, rowMapper);

        return dtoList;

    }
}
