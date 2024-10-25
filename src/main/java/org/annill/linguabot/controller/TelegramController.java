package org.annill.linguabot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController

public class TelegramController {

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @PostMapping
    @RequestMapping("/webhook")
    public String getBotPath() {
        return "";
    }

    public String getBotUsername() {
        return "";
    }
}
