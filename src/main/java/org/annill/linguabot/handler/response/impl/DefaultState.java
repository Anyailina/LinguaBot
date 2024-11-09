package org.annill.linguabot.handler.response.impl;

import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.enums.response.impl.DefaultEnum;
import org.annill.linguabot.handler.response.ResponseHandler;
import org.telegram.telegrambots.meta.api.objects.User;


public class DefaultState implements ResponseHandler {
    @Override
    public ResponseEnum getType() {
        return DefaultEnum.DEFAULT;
    }

    @Override
    public String process(String text, User user) {
        return DefaultEnum.DEFAULT.getMessage();
    }
}
