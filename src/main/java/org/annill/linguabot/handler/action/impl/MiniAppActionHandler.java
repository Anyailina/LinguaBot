package org.annill.linguabot.handler.action.impl;

import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MiniAppActionHandler implements ActionHandler {
    @Value("${message.open-miniapp}")
    private String openMiniAppString;
    @Value("${message.button-open-miniapp}")
    private String buttonOpenMiniAppString;
    @Value("${http.mini-app}")
    private String httpMiniApp;

    @Override
    public ActionEnum getType() {
        return ActionEnum.REPEAT_WORDS;
    }

    @Override
    public SendMessage process(String text, User user) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();


        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(openMiniAppString);
        button.setUrl(httpMiniApp);
        row.add(button);

        rows.add(row);
        inlineKeyboard.setKeyboard(rows);


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getId());
        sendMessage.setText(openMiniAppString);
        sendMessage.setReplyMarkup(inlineKeyboard);

        return sendMessage;
    }
}
