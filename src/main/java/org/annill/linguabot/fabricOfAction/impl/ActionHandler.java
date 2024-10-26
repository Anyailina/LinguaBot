package org.annill.linguabot.fabricOfAction.impl;

import org.annill.linguabot.enums.ActionEnum;

public interface ActionHandler {
    ActionEnum getType();

    String waitProcess();

    String process(String text, long chatId);
}
