package org.annill.linguabot.handler.response;

import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.handler.response.impl.DefaultState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseHandlerHelper {
    private final Map<ResponseEnum, ResponseHandler> responseHandlerMap;

    public ResponseHandlerHelper(List<ResponseHandler> listAction) {
        responseHandlerMap = new HashMap<>();
        listAction.forEach(responseHandler -> responseHandlerMap.put(responseHandler.getType(), responseHandler));
    }

    public String process(ResponseEnum responseEnum, String text, User user) {
        return responseHandlerMap.get(responseEnum).process(text, user);
    }

    public ResponseHandler findHandler(ResponseEnum responseEnum) {
        return responseHandlerMap.getOrDefault(responseEnum, new DefaultState());
    }
}
