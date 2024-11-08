package org.annill.linguabot.handler.action.impl;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.enums.response.impl.AddWordResponseEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.annill.linguabot.model.cache.SessionCache;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@AllArgsConstructor
public class AddWordActionHandler implements ActionHandler {
    private Cache cache;

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_WORD;
    }

    @Override
    public String process(String text, User user) {
        SessionCache sessionCache = new SessionCache()
                .setResponse(AddWordResponseEnum.NAME_FOLDER);
        cache.put(user.getId(), sessionCache);
        return AddWordResponseEnum.GET_NAME_FOLDER.getMessage();
    }
}
