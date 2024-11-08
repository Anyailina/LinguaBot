package org.annill.linguabot.handler.action.impl;


import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.enums.response.impl.AddFolderResponseEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.annill.linguabot.model.cache.SessionCache;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@AllArgsConstructor
public class AddFolderActionHandler implements ActionHandler {
    private Cache cache;

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_FOLDER;
    }

    @Override
    public String process(String text, User user) {
        SessionCache sessionCache = new SessionCache()
                .setResponse(AddFolderResponseEnum.NAME_FOLDER);
        cache.put(user.getId(), sessionCache);
        return AddFolderResponseEnum.NAME_FOLDER.getMessage();
    }
}
