package org.annill.linguabot.handler.response.impl.addWordStates;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.enums.response.impl.AddWordResponseEnum;
import org.annill.linguabot.handler.response.ResponseHandler;
import org.annill.linguabot.kafka.KafkaWordSuggestionProducer;
import org.annill.linguabot.model.cache.SessionCache;
import org.annill.linguabot.pattern.RegexPattern;
import org.annill.linguabot.service.WordService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AddWordState implements ResponseHandler {
    private final KafkaWordSuggestionProducer kafkaWordSuggestionProducer;
    private final WordService wordService;
    private final Cache cache;
    @Value("${message.mistake.translate-exists}")
    private String messageTranslationExists;
    @Value("${message.not_correct-input}")
    private String messageInputNotCorrect;


    @Override
    public ResponseEnum getType() {
        return AddWordResponseEnum.TRANSLATION;
    }

    @Override
    public String process(String translation, User user) {
        if (!RegexPattern.isMessageContainsOnlyLetters(translation)) {
            return messageInputNotCorrect;
        }
        Long userId = user.getId();
        SessionCache sessionCache = cache.get(userId, SessionCache.class);
        Long folderIds = Objects.requireNonNull(sessionCache).getCurrentFolderId();
        String word = sessionCache.getWord();

        if (wordService.existsSameWord(folderIds, word, translation, userId)) {
            return messageTranslationExists;
        }

        wordService.addWord(folderIds, word, translation, userId);
        kafkaWordSuggestionProducer.sendMessage(word, translation);
        cache.evict(userId);

        return AddWordResponseEnum.TRANSLATION.getMessage();
    }

}
