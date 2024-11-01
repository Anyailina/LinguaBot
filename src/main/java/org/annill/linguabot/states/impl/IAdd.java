package org.annill.linguabot.states.impl;


import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.states.context.AddContext;

import java.io.Serializable;


public interface IAdd extends Serializable {
    String getStatus();

    ResultStatusEnum processMessage(AddContext addContext, String text, long chatId);

    void nextState(AddContext addContext, String text, long chatId);

    String wrongAnswer();
}
