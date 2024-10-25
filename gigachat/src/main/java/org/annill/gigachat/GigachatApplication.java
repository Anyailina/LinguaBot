package org.annill.gigachat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class GigachatApplication {
    public static void main(String[] args) {
        SpringApplication.run(GigachatApplication.class, args);
    }

}
