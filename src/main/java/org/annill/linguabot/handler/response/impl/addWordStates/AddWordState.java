package org.annill.linguabot.handler.response.impl.addWordStates;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.enums.response.impl.AddWordResponseEnum;
import org.annill.linguabot.handler.response.ResponseHandler;
import org.annill.linguabot.kafka.producer.KafkaAddedWordProducer;
import org.annill.linguabot.model.Message;
import org.annill.linguabot.model.cache.SessionCache;
import org.annill.linguabot.pattern.RegexPattern;
import org.annill.linguabot.service.WordService;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AddWordState implements ResponseHandler {
    private final KafkaAddedWordProducer kafkaAddedWordProducer;
    private final WordService wordService;
    private final Cache cache;
    private final Message message;

    @Override
    public ResponseEnum getType() {
        return AddWordResponseEnum.TRANSLATION;
    }

    @Override
    public String process(String translation, User user) {
        if (!RegexPattern.isMessageContainsOnlyLetters(translation)) {
            return message.getNotCorrectInput();
        }
        Long chatId = user.getId();
        SessionCache sessionCache = cache.get(chatId, SessionCache.class);
        Long folderIds = Objects.requireNonNull(sessionCache).getCurrentFolderId();
        String word = sessionCache.getWord();

        if (wordService.existsSameWord(folderIds, word, translation, chatId)) {
            return message.getTranslateExists();
        }

        wordService.addWord(folderIds, word, translation, chatId);
        kafkaAddedWordProducer.sendMessageAddedWord(word, translation);
        cache.evict(chatId);

        return AddWordResponseEnum.TRANSLATION.getMessage();
    }

}
