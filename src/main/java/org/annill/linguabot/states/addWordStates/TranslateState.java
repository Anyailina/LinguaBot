package org.annill.linguabot.states.addWordStates;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.cache.WordCache;
import org.annill.linguabot.enums.AddWordStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.kafka.KafkaProducer;
import org.annill.linguabot.service.WordService;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TranslateState implements IAdd {
    private final KafkaProducer kafkaProducer;
    private final WordService wordService;
    @Value("${message.mistake.translate-exists}")
    private String messageTranslationExists;


    @Override
    public String getStatus() {
        return AddWordStateEnum.TRANSLATION.getStatesName();
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String phrase, long chatId) {
        WordCache wordCache = addContext.getExistingUserCacheData(chatId).getWordCache();
        if (wordService.existsSameWord(wordCache.getFolderName(), wordCache.getWord(), phrase, chatId)) {
            return ResultStatusEnum.MISTAKE;
        }
        return ResultStatusEnum.RIGHT;
    }

    @Override
    public void nextState(AddContext addContext, String translation, long chatId) {
        Cache cache = addContext.getCache();
        WordCache wordCache = addContext.getExistingUserCacheData(chatId).getWordCache();
        wordService.addWord(wordCache.getFolderName(), wordCache.getWord(), translation, chatId);
        kafkaProducer.sendMessage(wordCache.getWord(), translation);
        cache.evict(chatId);
    }


    @Override
    public String wrongAnswer() {
        return messageTranslationExists;
    }
}
