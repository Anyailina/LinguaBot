package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.handler.action.ActionHandlerHelper2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
@AllArgsConstructor
public class TelegramService2 {
    private final ActionHandlerHelper2 actionHandlerHelper;

    public SendMessage processUpdate(Update update) {
        if (update == null || !update.hasMessage() || !update.getMessage().hasText()) {
            return new SendMessage();
        }

        Message message = update.getMessage();
        User user = message.getFrom();

        String answer = actionHandlerHelper.process(message.getText(), user);

        return new SendMessage(String.valueOf(user.getId()), answer);
    }
}
