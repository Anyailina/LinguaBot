package org.annill.linguabot.states.addWordStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.cashe.UserCacheData;
import org.annill.linguabot.cashe.WordCash;
import org.annill.linguabot.controller.WordController;
import org.annill.linguabot.enums.AddWordStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TranslateState implements IAdd {
    private WordController wordController;

    @Override
    public String getStatus() {
        return AddWordStateEnum.TRANSLATE.getStatesName();
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String phrase, long chatId) {
        return ResultStatusEnum.RIGHT;
    }

    @Override
    public void nextState(AddContext addContext, String text, long chatId) {
        WordCash userCacheData = getWord(addContext,chatId);
        wordController.addWord(userCacheData.getFolderName(),userCacheData.getWord(),text,chatId);
    }

    @Override
    public String wrongAnswer() {
        return "";
    }

    private WordCash getWord(AddContext addContext,long chatId) {
        Cache cache = addContext.getCache();
        Cache.ValueWrapper wrapper = cache.get(chatId);
        UserCacheData userCacheData = (UserCacheData) wrapper.get();
        cache.evict(chatId);
        return userCacheData.getWordCash();
    }
}
