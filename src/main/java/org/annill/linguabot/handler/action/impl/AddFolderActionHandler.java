package org.annill.linguabot.handler.action.impl;


import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.enums.response.impl.AddFolderResponseEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.annill.linguabot.model.cache.SessionCache;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class AddFolderActionHandler implements ActionHandler {
    private Cache cache;

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_FOLDER;
    }

    @Override
    public BotApiMethod<?> process(String text, Update update) {

        SessionCache sessionCache = new SessionCache()
                .setResponse(AddFolderResponseEnum.NAME_FOLDER);
        Long chatId = update.getMessage().getFrom().getId();
        cache.put(chatId, sessionCache);
        return new SendMessage(String.valueOf(chatId), AddFolderResponseEnum.NAME_FOLDER.getMessage());
    }
}
