package org.annill.linguabot.handler.action;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.annill.linguabot.actions.ActionHandler2;
import org.annill.linguabot.enums.ActionEnum2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Getter
@Setter
@Slf4j
public class ActionHandlerHelper2 {
    private final Map<ActionEnum2, ActionHandler2> actionHandlersMap;

    public ActionHandlerHelper2(List<ActionHandler2> listAction) {
        actionHandlersMap = new HashMap<>();
        listAction.forEach(actionHandler -> actionHandlersMap.put(actionHandler.getType(), actionHandler));
    }

    public String process(String text, User user) {
        ActionEnum2 action = ActionEnum2.fromText(text);

        return actionHandlersMap.get(action).process(text, user);
    }
}