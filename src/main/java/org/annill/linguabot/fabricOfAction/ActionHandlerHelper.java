package org.annill.linguabot.fabricOfAction;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.annill.linguabot.cashe.UserCacheData;
import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

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

    public ActionHandlerHelper(List<ActionHandler> listAction, CacheManager cacheManager) {
        actionHandlersMap = new HashMap<>();
        cache = cacheManager.getCache("commands");
        listAction.forEach(actionHandler -> actionHandlersMap.put(actionHandler.getType(), actionHandler));
    }

    public String process(String text, long chatId) {
        ActionHandler action = actionHandlersMap.get(ActionEnum.fromText(text));
        IAdd iAdd = Optional.ofNullable(getCurrentProcess(chatId))
                .map(UserCacheData::getAddState)
                .orElse(null);
        return action.process(text, chatId, iAdd);
    }

    public UserCacheData getCurrentProcess(long chatId) {
        Cache.ValueWrapper wrapper = cache.get(chatId);
        return (wrapper != null) ? (UserCacheData) wrapper.get() : null;
    }
}
