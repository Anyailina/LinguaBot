package org.annill.linguabot.actions.abs.impl;

import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.states.impl.IAdd;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.Serializable;


public interface ActionHandler extends Serializable {
    ActionEnum getType();

    String process(String text, User user, IAdd iAdd);
}
