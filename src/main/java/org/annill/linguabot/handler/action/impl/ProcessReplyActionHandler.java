package org.annill.linguabot.handler.action.impl;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.enums.response.impl.DefaultEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.annill.linguabot.handler.response.ResponseHandlerHelper;
import org.annill.linguabot.model.cache.SessionCache;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProcessReplyActionHandler implements ActionHandler {
    private Cache cache;
    private ResponseHandlerHelper responseHandlerHelper;

    @Override
    public ActionEnum getType() {
        return ActionEnum.PROCESS_REPLY;
    }

    @Override
    public BotApiMethod<?> process(String text, Update update) {

        User user = update.getMessage() == null ? update.getCallbackQuery().getFrom() : update.getMessage().getFrom();
        Long chatId = user.getId();
        SessionCache sessionCache = cache.get(chatId, SessionCache.class);
        ResponseEnum responseEnum = Optional.ofNullable(sessionCache)
                .map(SessionCache::getResponse)
                .orElse(DefaultEnum.DEFAULT);
        String answer = responseHandlerHelper.findHandler(responseEnum)
                .process(text, user);
        return new SendMessage(String.valueOf(chatId), answer);
    }
}
