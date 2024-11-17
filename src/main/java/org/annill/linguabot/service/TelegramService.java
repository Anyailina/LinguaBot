package org.annill.linguabot.service;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.handler.action.ActionHandlerHelper;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramService {
    private final ActionHandlerHelper actionHandlerHelper;

    public BotApiMethod<?> processUpdate(Update update) {
        if (update == null) return new SendMessage();

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackData = callbackQuery.getData();
            if (callbackData.contains("_")) {
                String folderName = callbackData.split("_")[1];
                return actionHandlerHelper.process(folderName, update);
            }
            return actionHandlerHelper.process(callbackData, update);

        } else if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();
            return actionHandlerHelper.process(text, update);
        }
        return new SendMessage();
    }
}

