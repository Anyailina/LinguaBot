package org.annill.linguabot.service;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.handler.action.ActionHandlerHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
@RequiredArgsConstructor
public class TelegramService {
    private final ActionHandlerHelper actionHandlerHelper;
    @Value("${message.not_correct-input}")
    private String messageInputNotCorrect;
    private String regular = "^[а-яА-Яa-zA-Z/_]+$";

    public SendMessage processUpdate(Update update) {

        if (update == null || !update.hasMessage() || !update.getMessage().hasText()) {
            return new SendMessage();
        }

        Message message = update.getMessage();
        User user = message.getFrom();

        if ( !update.getMessage().getText().matches(regular)){
            return new SendMessage(String.valueOf(user.getId()),messageInputNotCorrect);
        }

        String answer = actionHandlerHelper.process(message.getText(), user);

        return new SendMessage(String.valueOf(user.getId()), answer);
    }
}
