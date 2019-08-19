package com.lessons.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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

    public void updateReviewed(final Integer id, final Boolean reviewed){
        logger.debug("updateReviewd() started");

            TransactionTemplate tt = new TransactionTemplate();
            tt.setTransactionManager(new DataSourceTransactionManager(dataSource));

            // This transaction will throw a TransactionTimedOutException after 60 seconds (causing the transaction to rollback)
            tt.setTimeout(60);

            tt.execute(new TransactionCallbackWithoutResult()
            {
                protected void doInTransactionWithoutResult(TransactionStatus aStatus)
                {
                    Map<String, Object> params = new HashMap<>();
                    params.put("id", id);
                    params.put("reviewed", reviewed);

                    String sql = "update reports set reviewed = :reviewed where id = :id returning *";
                    NamedParameterJdbcTemplate np = new NamedParameterJdbcTemplate(dataSource);

                    Map<String, Object> updatedRecord = np.queryForMap(sql, params);

                    int transactionId = getNextVal();

                    sql = "INSERT into reports_aud (rev, rev_type, id, version, description, display_name, reviewed) " +
                            "values (:rev, :rev_type, :id, :version, :description, :display_name, :reviewed)";

                    params.clear();
                    params.put("rev", transactionId);
                    params.put("rev_type", 1);
                    params.put("id", updatedRecord.get("id"));
                    params.put("version", updatedRecord.get("version"));
                    params.put("description", updatedRecord.get("description"));
                    params.put("display_name", updatedRecord.get("display_name"));
                    params.put("reviewed", updatedRecord.get("reviewed"));

                    np.update(sql, params);

                    logger.debug("Updated {} record(s)", updatedRecord);
                }
            });

            logger.debug("Transaction finished");
        }


    public boolean recordExists(Integer recordId){

        String sql = "SELECT id FROM reports WHERE id = " + recordId;

        JdbcTemplate jt = new JdbcTemplate(dataSource);
        SqlRowSet rs = jt.queryForRowSet(sql);
        return rs.next();
    }
}
