package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.controller.UserController;
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
    private final UserController userController;


    public SendMessage processUpdate(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return null;
        }
        Message message = update.getMessage();
        long userId = message.getChatId();
        processUserId(message.getChatId());

        String answer = Optional.ofNullable(actionHandlerHelper.getCurrentProcess(userId))
                .map(action -> {
                    String result = action.process(message.getText(), userId);
                    actionHandlerHelper.deleteCurrentProcess(userId);
                    return result;
                })
                .orElseGet(() -> actionHandlerHelper.process(message.getText(), userId));


        return new SendMessage(String.valueOf(userId), answer);
    }

    public void processUserId(long userId) {
        userController.addUser(userId);
    }
}
