package com.lessons.services;

import com.lessons.models.SearchDTO;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ElasticSearchService {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchService.class);

    @Value("${es.url}")
    private String esUrl;

    @Resource
    private AsyncHttpClient asyncHttpClient;

    public ElasticSearchService(){
        logger.debug("ElasticSearchService Constructor called");
    }

    public String runSearchOld(String rawQuery) throws Exception {
        Response response = asyncHttpClient.preparePost(esUrl + "/reports/_search?pretty=true")
                .setHeader("accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .execute()
                .get();
        String result = response.getResponseBody();

        return result;
    }

    public String runSearch(SearchDTO searchParams) throws Exception {

        String url = esUrl + "/reports/_search?pretty=true";
        String jsonRequest = buildJsonRequest(searchParams.getRawQuery(), searchParams.getFilters());

        Response response = asyncHttpClient.preparePost(url)
                .setHeader("accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .setBody(jsonRequest)
                .execute()
                .get();



        String result = response.getResponseBody();

        return result;
    }

    private String buildJsonRequest(String rawQuery, List<String> filters){
        String baseFilter = "{\"term\":{\"%s\":\"%s\"}},";
        String filterList = "";

        if (filters != null) {
            for (String filter : filters) {
                String[] parts = StringUtils.split(filter, ":");
                String filterKey = handleSpecialJsonCharacters(parts[0]);
                String filterValue = handleSpecialJsonCharacters(parts[1]);
                String formattedFilter = String.format(baseFilter, filterKey, filterValue);
                filterList = filterList + formattedFilter;
            }
            filterList = StringUtils.substring(filterList, 0, filterList.length() - 1);
        }

        String baseJson = "{\n" +
                "  \"query\":{\n" +
                "    \"bool\":{\n" +
                "      \"must\": [{\n" +
                "        \"query_string\": {\n" +
                "          \"query\": \" %s \"\n" +
                "          }\n" +
                "        }],\n" +
                "        \"filter\":[ %s ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        String formatedQuery = handleSpecialJsonCharacters(rawQuery);

        return String.format(baseJson, formatedQuery, filterList);
    }

    private String handleSpecialJsonCharacters(String targetString){
        targetString = targetString.replace("\"", "\\\"");

        return targetString;
    }

}
