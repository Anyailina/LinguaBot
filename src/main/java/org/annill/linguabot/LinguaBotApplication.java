package org.annill.linguabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LinguaBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinguaBotApplication.class, args);
    }
}
