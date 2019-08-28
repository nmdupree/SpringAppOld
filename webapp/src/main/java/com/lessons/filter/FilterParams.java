package com.lessons.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FilterParams {
    private static Logger logger = LoggerFactory.getLogger(FilterParams.class);

    private String sqlWhereClause;
    Map<String,Object> sqlParams;

    public Map<String, Object> getSqlParams() {
        return sqlParams;
    }

    public void setSqlParams(Map<String, Object> sqlParams) {
        this.sqlParams = sqlParams;
    }

    public String getSqlWhereClause() {
        return sqlWhereClause;
    }

    public void setSqlWhereClause(String sqlWhereClause) {
        this.sqlWhereClause = sqlWhereClause;
    }
}
