package org.annill.linguabot.states.addWordStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.cashe.WordCash;
import org.annill.linguabot.controller.WordController;
import org.annill.linguabot.enums.AddWordStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TranslateState implements IAdd {
    private WordController wordController;
    @Value("${message.mistake.translate-exists}")
    private String messageTranslationExists;

    @Override
    public String getStatus() {
        return AddWordStateEnum.TRANSLATION.getStatesName();
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String phrase, long chatId) {
        return ResultStatusEnum.RIGHT;
    }

    @Override
    public void nextState(AddContext addContext, String text, long chatId) {
        Cache cache = addContext.getCache();
        WordCash wordCash = addContext.getExistingUserCacheData(chatId).getWordCash();
        wordController.addWord(wordCash.getFolderName(), wordCash.getWord(), text, chatId);
        cache.evict(chatId);
    }

    @Override
    public String wrongAnswer() {
        return messageTranslationExists;
    }
}
