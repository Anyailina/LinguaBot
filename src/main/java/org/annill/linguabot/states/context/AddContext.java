package org.annill.linguabot.states.context;


import lombok.Getter;
import lombok.Setter;
import org.annill.linguabot.actions.abs.impl.ActionHandler;
import org.annill.linguabot.caсhe.UserCacheData;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.cache.Cache;

import java.util.Optional;

@Setter
@Getter
public class AddContext {
    private IAdd iAdd;
    private ActionHandler actionHandler;
    private Cache cache;

    public AddContext(IAdd iAdd, ActionHandler actionHandler, Cache cache) {
        this.iAdd = iAdd;
        this.actionHandler = actionHandler;
        this.cache = cache;
    }

    public ResultStatusEnum processMessage(String text, long chatId) {
        return iAdd.processMessage(this, text, chatId);
    }

    public UserCacheData getExistingUserCacheData(long chatId) {
        return Optional.ofNullable(cache.get(chatId))
                .map(Cache.ValueWrapper::get)
                .map(UserCacheData.class::cast)
                .orElseThrow(IllegalArgumentException::new);
    }
}
