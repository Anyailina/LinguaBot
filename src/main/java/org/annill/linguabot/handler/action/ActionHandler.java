package org.annill.linguabot.handler.action;

import org.annill.linguabot.enums.action.ActionEnum;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.Serializable;


public interface ActionHandler extends Serializable {
    ActionEnum getType();

    SendMessage process(String text, User user);
}
