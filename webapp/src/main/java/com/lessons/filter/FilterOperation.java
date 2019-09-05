package com.lessons.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public enum FilterOperation {

    /*
     * FilterService
     *
     * Used to convert the list of filter strings into a SQL where clause
     *
     * The passed-in list Of String can have any of these formats:
     *    Fieldname <separator> EQUALS          value
     *    Fieldname <separator> GREATER         value
     *    Fieldname <separator> GREATER_EQUAL   value
     *    Fieldname <separator> LESS            value
     *    Fieldname <separator> LESS_EQUAL      value
     *    Fieldname <separator> BETWEEN         value1 <separator> value2                        (applies to dates and numeric fields only)
     *    Fieldname <separator> IN              value1 <separator> value2 <separator> value3....
     *    Fieldname <separator> NOTIN           value1 <separator> value2 <separator> value3....
     *    Fieldname <separator> CONTAINS        value1
     *    Fieldname <separator> ICONTAINS       value1
     *    Fieldname <separator> ISNULL
     *    Fieldname <separator> ISNOTNULL
     *
     */

    EQUALS("EQUALS", "==", 3, "%s = :%s::Integer"),
    STRING_EQUALS("STRING_EQUALS", "==", 3, "%s = :%s"),
    GREATER("GREATER", "==", 3, "%s > :%s::Integer"),
    GREATER_EQUAL("GREATER_EQUAL", "==", 3, "%s >= :%s::Integer"),
    LESS("LESS", "==", 3, "%s < :%s::Integer"),
    LESS_EQUAL("LESS_EQUAL", "==", 3, "%s <= :%s::Integer"),
    BETWEEN("BETWEEN", "==", 4, "%s BETWEEN :%s::TIMESTAMP AND :%s::TIMESTAMP"),
    IN("IN", ">=", 3, "%s IN :%s"),
    NOTIN("NOTIN", ">=", 3, "%s NOT IN :%s"),
    CONTAINS("CONTAINS", "==", 3, "%s LIKE :%s"),
    ICONTAINS("ICONTAINS", "==", 3, "%s ILIKE :%s"),
    ISNULL("ISNULL", "==", 2, "%s IS NULL"),
    ISNOTNULL("ISNOTNULL", "==", 2, "%s IS NOT NULL");


    private Logger logger = LoggerFactory.getLogger(FilterOperation.class);

    private int tokenCount;
    private String compareOperation;
    private String filterOperation;
    private String sqlOperator;

    private FilterOperation(String filterOperation, String compareOperation, int tokenCount, String sqlOperator) {
        logger.debug("FilterOperation() called");
        this.tokenCount = tokenCount;
        this.compareOperation = compareOperation;
        this.filterOperation = filterOperation;
        this.sqlOperator = sqlOperator;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public String getCompareOperation() {
        return compareOperation;
    }

    public String getFilterOperation() {
        return filterOperation;
    }

    public String getSqlOperator() {
        return sqlOperator;
    }

    public static FilterOperation getOperation(String operationName) {
        List<FilterOperation> allOperation = Arrays.asList(FilterOperation.values());

        for (FilterOperation fo : allOperation) {
            if (operationName.equalsIgnoreCase(fo.getFilterOperation())) {
                return fo;
            }
        }
        return null;
    }
}