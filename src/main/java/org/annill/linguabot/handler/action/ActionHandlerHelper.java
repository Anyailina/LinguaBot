package org.annill.linguabot.handler.action;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.annill.linguabot.enums.action.ActionEnum;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Getter
@Setter
@Slf4j
public class ActionHandlerHelper {
    private final Map<ActionEnum, ActionHandler> actionHandlersMap;
    private Cache cache;

    public ActionHandlerHelper(List<ActionHandler> listAction, Cache cache) {
        actionHandlersMap = new HashMap<>();
        this.cache = cache;
        listAction.forEach(actionHandler -> actionHandlersMap.put(actionHandler.getType(), actionHandler));
    }

    public BotApiMethod<?> process(String text, Update update) {
        ActionHandler action = actionHandlersMap.get(ActionEnum.fromText(text));
        return action.process(text, update);
    }
}
