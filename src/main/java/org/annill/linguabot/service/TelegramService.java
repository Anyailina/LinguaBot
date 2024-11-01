package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.actions.ActionHandlerHelper;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
@AllArgsConstructor
public class TelegramService {
    private final ActionHandlerHelper actionHandlerHelper;

    public SendMessage processUpdate(Update update) {
        if (update == null || !update.hasMessage() || !update.getMessage().hasText()) {
            return new SendMessage();
        }

        Message message = update.getMessage();
        User user = message.getFrom();

        String answer = actionHandlerHelper.getCurrentProcess(user)
                .map(action -> action.getActionState().process(message.getText(), user, action.getAddState()))
                .orElseGet(() -> actionHandlerHelper.process(message.getText(), user));

        return new SendMessage(String.valueOf(user.getId()), answer);
    }
}
