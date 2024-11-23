package org.annill.linguabot.handler.action.impl;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.annill.linguabot.model.Message;
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
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MiniAppActionHandler implements ActionHandler {
    private final UserService userService;
    private final Message message;
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
        Optional<UserDto> userDto = userService.getUserIdByChatId(chatId);

        if (userDto.isEmpty()) {
            return null;
        }

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(message.getOpenMiniapp());
        button.setUrl(httpMiniApp + userDto.get().getId());

        row.add(button);
        rows.add(row);
        inlineKeyboard.setKeyboard(rows);

        return createMessage(userDto.get(), inlineKeyboard);
    }

    private SendMessage createMessage(UserDto userDto, InlineKeyboardMarkup inlineKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userDto.getChatId());
        sendMessage.setText(message.getButtonOpenMiniapp());
        sendMessage.setReplyMarkup(inlineKeyboard);
        return sendMessage;
    }
}
