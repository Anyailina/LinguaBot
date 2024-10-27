package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.fabricOfAction.ActionHandlerHelper;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TelegramService {
    private final ActionHandlerHelper actionHandlerHelper;

    public SendMessage processUpdate(Update update) {
        if (update == null || !update.hasMessage() || !update.getMessage().hasText()) {
            return new SendMessage();
        }

        Message message = update.getMessage();
        long userId = message.getChatId();

        String answer = Optional.ofNullable(actionHandlerHelper.getCurrentProcess(userId))
                .map(action -> action.getActionState().process(message.getText(), userId, action.getAddState()))
                .orElseGet(() -> actionHandlerHelper.process(message.getText(), userId));

        return new SendMessage(String.valueOf(userId), answer);
    }

}
