package org.annill.linguabot;

import org.annill.linguabot.service.RepeatWordService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(RepeatWordService.class)
public class LinguaBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinguaBotApplication.class, args);
    }
}
