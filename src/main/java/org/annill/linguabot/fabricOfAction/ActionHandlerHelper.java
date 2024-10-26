package org.annill.linguabot.fabricOfAction;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.fabricOfAction.commands.DefaultActionHandler;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

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

    public ActionHandlerHelper(List<ActionHandler> listAction, CacheManager cacheManager) {
        actionHandlersMap = new HashMap<>();
        cache = cacheManager.getCache("commands");
        listAction.forEach(actionHandler -> actionHandlersMap.put(actionHandler.getType(), actionHandler));
    }

    public String process(String text, long chatId) {
        ActionHandler action = actionHandlersMap.get(ActionEnum.fromText(text));
        if (!(action instanceof DefaultActionHandler)) {
            cache.put(chatId, action);
        }
        return action.waitProcess();
    }

    public ActionHandler getCurrentProcess(long chatId) {
        Cache.ValueWrapper wrapper = cache.get(chatId);
        return (wrapper != null) ? (ActionHandler) wrapper.get() : null;
    }

    public void deleteCurrentProcess(long chatId) {
        cache.evict(chatId);
    }
}
