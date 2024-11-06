package org.annill.linguabot.controller;

import org.annill.linguabot.service.TelegramService;
import org.annill.linguabot.service.TelegramService2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RestController
@RequestMapping("/")
public class TelegramController extends DefaultAbsSender {
    private final TelegramService telegramService;
    private final TelegramService2 telegramService2;

    public TelegramController(DefaultBotOptions options,
                              String botToken,
                              TelegramService telegramService,
                              TelegramService2 telegramService2) {
        super(options, botToken);
        this.telegramService = telegramService;
        this.telegramService2 = telegramService2;
    }

    @PostMapping("/webhook")
    public void getUpdate(@RequestBody Update update) throws TelegramApiException {
        execute(telegramService.processUpdate(update));
    }

    @PostMapping("/webhook2")
    public void processUpdate2(@RequestBody Update update) throws TelegramApiException {
        execute(telegramService2.processUpdate(update));
    }

    @PostMapping("/webhookTest")
    public SendMessage testGetUpdate(@RequestBody Update update) {
        return telegramService.processUpdate(update);
    }
}