package org.annill.linguabot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class TelegramController extends TelegramWebhookBot {
    @Value("${telegramBot.name}")
    private String telegramName;
    @Value("${telegramBot.token}")
    private String telegramToken;

    @PostMapping("/webhook")
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotPath() {
        return telegramToken;
    }

    @Override
    public String getBotUsername() {
        return telegramName;
    }
}
