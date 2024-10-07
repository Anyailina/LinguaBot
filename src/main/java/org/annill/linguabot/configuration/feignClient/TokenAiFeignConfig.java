package org.annill.linguabot.configuration.feignClient;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


public class TokenAiFeignConfig {
    @Value("${ai.clientSecret}")
    private String clientSecret;
    @Value("${ai.authData}")
    private String authData;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/x-www-form-urlencoded");
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("RqUID", clientSecret);
            requestTemplate.header("Authorization", "Basic " + authData);
        };
    }
}
