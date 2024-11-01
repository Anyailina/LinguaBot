package org.annill.linguabot.actions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.annill.linguabot.actions.abs.impl.ActionHandler;
import org.annill.linguabot.caсhe.UserCacheData;
import org.annill.linguabot.enums.ActionEnum;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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

    public String process(String text, User user) {
        ActionHandler action = actionHandlersMap.get(ActionEnum.fromText(text));
        return action.process(text, user, null);
    }

    public Optional<UserCacheData> getCurrentProcess(User user) {
        Cache.ValueWrapper wrapper = cache.get(user.getId());
        return Optional.ofNullable(wrapper != null ? (UserCacheData) wrapper.get() : null);
    }
}
