package org.annill.linguabot.handler.action.impl;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MiniAppActionHandler implements ActionHandler {
    private final UserService userService;
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
    public BotApiMethod<?> process(String text, Update update) {
        Long chatId = update.getMessage().getFrom().getId();
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        UserDto userDto = userService.getUserIdByChatId(chatId);

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(openMiniAppString);
        button.setUrl(httpMiniApp + userDto.getId());

        row.add(button);
        rows.add(row);
        inlineKeyboard.setKeyboard(rows);

        return createMessage(userDto, inlineKeyboard);
    }

    private SendMessage createMessage(UserDto userDto, InlineKeyboardMarkup inlineKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userDto.getChatId());
        sendMessage.setText(buttonOpenMiniAppString);
        sendMessage.setReplyMarkup(inlineKeyboard);
        return sendMessage;
    }
}
