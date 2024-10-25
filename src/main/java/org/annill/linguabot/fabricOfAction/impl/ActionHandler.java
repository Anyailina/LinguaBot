package org.annill.linguabot.fabricOfAction.impl;

import org.annill.linguabot.fabricOfAction.ActionEnum;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ActionHandler {
    ActionEnum getType();
    String process(Update update);
}
