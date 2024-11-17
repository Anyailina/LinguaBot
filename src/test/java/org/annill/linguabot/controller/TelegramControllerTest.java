package org.annill.linguabot.controller;

import lombok.AllArgsConstructor;
import org.annill.linguabot.service.TelegramService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class TelegramControllerTest {
    private TelegramService telegramService;

    @PostMapping("/webhookTest")
    public BotApiMethod<?> testGetUpdate(@RequestBody Update update) {
        return telegramService.processUpdate(update);
    }
}
