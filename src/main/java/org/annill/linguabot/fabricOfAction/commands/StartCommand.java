package org.annill.linguabot.fabricOfAction.commands;

import lombok.AllArgsConstructor;
import org.annill.linguabot.controller.UserController;
import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartCommand implements ActionHandler {
    private UserController userController;
    @Value("${message.start}")
    private String messageStartReturn;

    @Override
    public ActionEnum getType() {
        return ActionEnum.START;
    }

    @Override
    public String process(String text, long chatId, IAdd iAdd) {
        userController.addUser(chatId);
        return messageStartReturn;
    }
}
