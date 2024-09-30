package org.annill.linguabot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
@PropertySource("application.properties")
public class TelegramConfig {
    @Value("${bot.token}")
    private String token;

    @Bean
    public String createToken() {
        return token;
    }

    @Bean
    public DefaultBotOptions createOptions() {
        return new DefaultBotOptions();
    }
}
