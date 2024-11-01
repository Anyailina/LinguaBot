package org.annill.linguabot.states.addWordStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.caсhe.WordCache;
import org.annill.linguabot.enums.AddWordStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.service.WordService;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TranslateState implements IAdd {
    private WordService wordService;
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
        WordCache wordCache = addContext.getExistingUserCacheData(chatId).getWordCache();
        wordService.addWord(wordCache.getFolderName(), wordCache.getWord(), text, chatId);
        cache.evict(chatId);
    }

    @Override
    public String wrongAnswer() {
        return messageTranslationExists;
    }
}
