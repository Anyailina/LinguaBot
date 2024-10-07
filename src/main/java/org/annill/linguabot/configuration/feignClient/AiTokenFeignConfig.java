package org.annill.linguabot.configuration.feignClient;

import feign.RequestInterceptor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
public class AiTokenFeignConfig {
    @Value("${ai.clientSecret}")
    private String clientSecret;
    @Value("${ai.authData}")
    private String authData;

    @Bean
    public RequestInterceptor tokenRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/x-www-form-urlencoded");
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("RqUID", clientSecret);
            requestTemplate.header("Authorization", "Basic " + authData);
        };
    }
}
