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
public class WordState implements IAdd {
    private TranslateState translateState;
    private WordController wordController;

    @Override
    public String getStatus() {
        return AddWordStateEnum.WORD.getStatesName();
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String text, long chatId) {
        return ResultStatusEnum.RIGHT;
    }

    @Override
    public void nextState(AddContext addContext, String text, long chatId) {
        Cache cache = addContext.getCache();
        UserCacheData userCacheData = getOldUserCashData(cache, chatId);
        wordController.getWord(userCacheData.getWordCash().getFolderName(), text, chatId);

        createNewUserCacheData(addContext, text, chatId);
        addContext.setIAdd(translateState);
    }

    @Override
    public String wrongAnswer() {
        return "";
    }

    private UserCacheData createNewUserCacheData(AddContext addContext, String phrase, long chatId) {
        Cache cache = addContext.getCache();
        UserCacheData userCacheData = getOldUserCashData(cache, chatId);
        WordCash wordCash = new WordCash(userCacheData.getWordCash().getFolderName(), phrase);
        UserCacheData newuserCacheData = new UserCacheData(translateState, addContext.getActionHandler(), wordCash);
        cache.put(chatId, newuserCacheData);
        return newuserCacheData;
    }

    private UserCacheData getOldUserCashData(Cache cache, long chatId) {
        Cache.ValueWrapper wrapper = cache.get(chatId);
        UserCacheData userCacheData = (UserCacheData) wrapper.get();
        return userCacheData;
    }
}
