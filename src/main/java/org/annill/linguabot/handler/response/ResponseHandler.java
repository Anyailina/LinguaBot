package org.annill.linguabot.handler.response;

import org.annill.linguabot.enums.response.ResponseEnum;
import org.telegram.telegrambots.meta.api.objects.User;

public interface ResponseHandler {

    ResponseEnum getType();

    String process(String text, User user);
}
