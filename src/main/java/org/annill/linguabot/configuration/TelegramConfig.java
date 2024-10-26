package org.annill.linguabot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
public class TelegramConfig {
    @Value("${telegramBot.token}")
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