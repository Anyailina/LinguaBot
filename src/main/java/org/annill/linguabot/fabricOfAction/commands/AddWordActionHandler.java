package org.annill.linguabot.fabricOfAction.commands;

import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;

public class AddWordActionHandler implements ActionHandler {
    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_WORD;
    }

    @Override
    public String waitProcess() {
        return "";
    }

    @Override
    public String process(String text, long chatId) {
        return "";
    }
}
