package org.annill.linguabot.fabricOfAction.commands;

import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultActionHandler implements ActionHandler {
    @Value("${message.default}")
    private String messageUnknownCommand;

    @Override
    public ActionEnum getType() {
        return ActionEnum.DEFAULT;
    }

    @Override
    public String process(String text, long chatId, IAdd iAdd) {
        return messageUnknownCommand;
    }
}
