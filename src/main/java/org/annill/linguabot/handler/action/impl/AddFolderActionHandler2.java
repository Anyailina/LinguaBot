package org.annill.linguabot.handler.action.impl;

import lombok.AllArgsConstructor;
import org.annill.linguabot.actions.ActionHandler2;
import org.annill.linguabot.enums.ActionEnum2;
import org.annill.linguabot.enums.ResponseEnum;
import org.annill.linguabot.model.cache.SessionCache;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@AllArgsConstructor
public class AddFolderActionHandler2 implements ActionHandler2 {

    protected Cache cache;

    @Override
    public ActionEnum2 getType() {
        return ActionEnum2.ADD_FOLDER;
    }

    @Override
    public String process(String text, User user) {
        SessionCache sessionCache = new SessionCache()
                .setResponse(ResponseEnum.TYPE_FOLDER_NAME);

        cache.put(user.getId(), sessionCache);

        return ResponseEnum.TYPE_FOLDER_NAME.getMessage();
    }
}