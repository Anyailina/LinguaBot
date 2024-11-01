package org.annill.linguabot.actions.commands;

import org.annill.linguabot.actions.abs.impl.ActionHandler;
import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class DefaultActionHandler implements ActionHandler {
    @Value("${message.default}")
    private String messageUnknownCommand;

    @Override
    public ActionEnum getType() {
        return ActionEnum.DEFAULT;
    }

    @Override
    public String process(String text, User user, IAdd iAdd) {
        return messageUnknownCommand;
    }
}
