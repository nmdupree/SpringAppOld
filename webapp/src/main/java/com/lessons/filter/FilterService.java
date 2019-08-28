package com.lessons.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



import javax.annotation.PostConstruct;
import java.util.List;


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
@Service("com.lessons.filter.FilterService")
public class FilterService {
    private static final Logger logger = LoggerFactory.getLogger(FilterService.class);

    public static final String FILTER_SEPARATOR = "~";

    @PostConstruct
    public void init() {
        logger.debug("init() started.");

    }

    /**
     * Returns false if the passed-in list of filters is invalid in anyway
     */
    public boolean areFiltersValid(List<String> aFilters) {
        logger.debug("areFiltersValid() started.");

        // Are the filter operation strings valid -- e.g., are they either BETWEEN, IN, CONTIANS, ICONTAINS, ISNULL, or ISNOTNULL
        // Are filters in the correct format -- e.g., BETWEEN requires 2 values
        // Do the field names correspond to database columns?

        if (aFilters == null) {
            throw new RuntimeException("aFliters is empty");
        }

        for (String filter: aFilters){
            String[] parts = StringUtils.split(filter, FILTER_SEPARATOR);
            String fieldName = parts[0];
            String operationName = parts[1];

            FilterOperation businessRules = FilterOperation.getOperation(operationName);

            if (businessRules == null) {
                logger.warn("User entered an operation name which is not one of the accepted values.");
                return false;
            }

            int expectedTokenCount = businessRules.getTokenCount();
            int actualTokenCount = parts.length;
            String comparisonOperator = businessRules.getCompareOperation();

            if (comparisonOperator.equalsIgnoreCase("==")) {
                if (expectedTokenCount != actualTokenCount) {
                    logger.warn("Exactly {} tokens expected, {} provided.", expectedTokenCount, actualTokenCount);
                    return false;
                }
            } else if (comparisonOperator.equalsIgnoreCase(">=")) {
                if (actualTokenCount < expectedTokenCount) {
                    logger.warn("At least {} tokens expected, {} provided.", expectedTokenCount, actualTokenCount);
                    return false;
                }
            }
            else {
                throw new RuntimeException("FilterOperation supplied an unexpected comparison operator.");
            }


        }  // end for loop
        return true;

    }


    /**
     * Return a SqlDetails that holds the SQL and the bind variables
     *
     * Approach:
     *  1) Loop through each fliter string
     *     a) Split-up the string by the separator character
     *     b) Get the operation string
     *     c) Pull the fields out
     *     d) Add the field values to the map of bind variables
     *     e) Append to the SQL string
     *  2) Create a FilterParams object and store the map of bind variables and SQL string in it
     *  3) Return the FilterParams object
     *
     * @param aFilters holds a list of Strings that hold filter operations
     * @return FilterParams object that holds the SQL partial where clause and a map of parameters
     */
    public FilterParams getFilterParamsForFilters(List<String> aFilters) {
        logger.debug("getFilterClause() started.");

      return null;
    }

    /**
     * Convert the passed-in list of Strings into an Order By clause
     *
     * @param aSortFields holds a list of strings in the formt of
     *                          "field>"  --> ORDER BY field ASC
     *                          "field<"  --> ORDER BY field DESC
     * @return
     */
    public String getSqlOrderByClauseForSortFields(List<String> aSortFields) {
        logger.debug("getSqlOrderByClauseForSortFields() started.");
        return null;
    }
}