package org.annill.linguabot.handler.action.impl;


import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.enums.response.impl.AddFolderResponseEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.annill.linguabot.model.cache.SessionCache;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    public SendMessage process(String text, User user) {
        SessionCache sessionCache = new SessionCache()
                .setResponse(AddFolderResponseEnum.NAME_FOLDER);
        Long userId = user.getId();
        cache.put(userId, sessionCache);
        return new SendMessage(String.valueOf(userId), AddFolderResponseEnum.NAME_FOLDER.getMessage());
    }
}
