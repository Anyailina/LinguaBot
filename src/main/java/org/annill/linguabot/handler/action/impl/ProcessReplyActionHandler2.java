package org.annill.linguabot.handler.action.impl;

import lombok.AllArgsConstructor;
import org.annill.linguabot.actions.ActionHandler2;
import org.annill.linguabot.enums.ActionEnum2;
import org.annill.linguabot.enums.ResponseEnum;
import org.annill.linguabot.handler.response.ResponseHandlerHelper;
import org.annill.linguabot.model.cache.SessionCache;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProcessReplyActionHandler2 implements ActionHandler2 {

    private Cache cache;
    private ResponseHandlerHelper responseHandlerHelper;

    @Override
    public ActionEnum2 getType() {
        return ActionEnum2.PROCESS_REPLY;
    }

    @Override
    public String process(String text, User user) {
        SessionCache sessionCache = cache.get(user.getId(), SessionCache.class);

        ResponseEnum responseEnum = Optional.ofNullable(sessionCache)
                .map(SessionCache::getResponse)
                .orElse(ResponseEnum.DEFAULT);

        return responseHandlerHelper.findHandler(responseEnum)
                .process(text, user);
    }
}
