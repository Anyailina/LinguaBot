package org.annill.linguabot.handler.response.impl;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.ResponseEnum;
import org.annill.linguabot.handler.response.ResponseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@AllArgsConstructor
@Component
public class DefaultResponseHandler implements ResponseHandler {

    @Override
    public ResponseEnum getType() {
        return ResponseEnum.DEFAULT;
    }

    @Override
    public String process(String text, User user) {
        return "Не могу обработать запрос - начните заново";
    }
}