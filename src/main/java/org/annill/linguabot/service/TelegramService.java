package org.annill.linguabot.service;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.enums.PageEnum;
import org.annill.linguabot.handler.action.ActionHandlerHelper;
import org.annill.linguabot.ui.FolderNavigationHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
@RequiredArgsConstructor
public class TelegramService {
    private final ActionHandlerHelper actionHandlerHelper;
    private final FolderNavigationHandler folderNavigationHandler;

    public BotApiMethod<?> processUpdate(Update update) {
        if (update == null) return new SendMessage();

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackData = callbackQuery.getData();
            if (PageEnum.NEXT_PAGE.getMessage().equals(callbackData) || PageEnum.PREVIOUS_PAGE.getMessage().equals(callbackData)) {
                return folderNavigationHandler.changePage(callbackQuery);
            } else {
                String folderName = callbackData.split("_")[1];
                return actionHandlerHelper.process(folderName, callbackQuery.getFrom());
            }
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            User user = message.getFrom();
            String text = message.getText();
            return actionHandlerHelper.process(text, user);
        }
        return new SendMessage();
    }
}

