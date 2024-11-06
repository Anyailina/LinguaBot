package org.annill.linguabot.handler.action;

import org.annill.linguabot.enums.ActionEnum2;
import org.telegram.telegrambots.meta.api.objects.User;


public interface ActionHandler2 {

    ActionEnum2 getType();

    String process(String text, User user);
}
