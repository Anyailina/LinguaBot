package org.annill.linguabot.handler.action;

import org.annill.linguabot.enums.action.ActionEnum;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.Serializable;


public interface ActionHandler extends Serializable {
    ActionEnum getType();

    String process(String text, User user);
}
