package org.annill.linguabot.fabricOfAction.impl;

import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.states.impl.IAdd;

public interface ActionHandler {
    ActionEnum getType();
    String process(String text, long chatId, IAdd iAdd);
}
