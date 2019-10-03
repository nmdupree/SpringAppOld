package com.lessons.config;

import com.ning.http.client.AsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class HttpClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientConfig.class);

    @Bean
    public AsyncHttpClient asyncHttpClient() {
        logger.debug("aSync Bean Created");
        return new AsyncHttpClient();
    }

    public HttpClientConfig(){
        logger.debug("HttpClientConfig Contstructor called.");
    }


}