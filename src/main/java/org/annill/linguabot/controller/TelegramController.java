package org.annill.linguabot.controller;


import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RestController
@RequestMapping("/")
public class TelegramController extends DefaultAbsSender {

    protected TelegramController(DefaultBotOptions options, String botToken) {
        super(options, botToken);
    }

    @PostMapping("/")
    public void getUpdate(@RequestBody Update update) throws TelegramApiException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            SendMessage messageToUser = new SendMessage();
            messageToUser.setChatId(chatId);
            messageToUser.setText("Аделина кидалово");
            execute(messageToUser);
        }
    }

}
