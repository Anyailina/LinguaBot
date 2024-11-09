package org.annill.linguabot.handler.action.impl;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.annill.linguabot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@AllArgsConstructor
public class StartActionHandler implements ActionHandler {
    private UserService userService;
    @Value("${message.start}")
    private String messageStartReturn;
    @Value("${message.user-registered}")
    private String messageUserRegistered;

    @Override
    public ActionEnum getType() {
        return ActionEnum.START;
    }

    @Override
    public BotApiMethod<?> process(String text, User user) {
        String answer = userService.addUser(user) == null ? messageUserRegistered : messageStartReturn;
        return new SendMessage(String.valueOf(user.getId()), answer);
    }

}
