package org.annill.linguabot.handler.action;

import org.annill.linguabot.enums.action.ActionEnum;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;


public interface ActionHandler extends Serializable {
    ActionEnum getType();

    BotApiMethod<?> process(String text, Update update);
}
