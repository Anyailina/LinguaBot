package org.annill.linguabot.actions.commands;

import lombok.AllArgsConstructor;
import org.annill.linguabot.actions.abs.impl.ActionHandler;
import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.service.UserService;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@AllArgsConstructor
public class StartCommand implements ActionHandler {
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
    public String process(String text, User user, IAdd iAdd) {
        return userService.addUser(user) == null ? messageUserRegistered : messageStartReturn;
    }
}
