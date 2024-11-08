package org.annill.linguabot.handler.response.impl.addWordStates;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.enums.response.impl.AddWordResponseEnum;
import org.annill.linguabot.handler.response.ResponseHandler;
import org.annill.linguabot.kafka.KafkaProducer;
import org.annill.linguabot.model.cache.SessionCache;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.service.WordService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AddWordWithSuggestionState implements ResponseHandler {
    private final KafkaProducer kafkaProducer;
    private final WordService wordService;
    private final Cache cache;
    @Value("${message.mistake.incorrect-number}")
    private String messageIncorrectNumber;
    @Value("${message.mistake.translate-exists}")
    private String messageTranslationExists;

    @Override
    public ResponseEnum getType() {
        return AddWordResponseEnum.SUGGEST_TRANSLATION;
    }

    public String process(String text, User user) {
        Long userId = user.getId();
        SessionCache sessionCache = getSessionCache(userId);
        Long folderIds = Objects.requireNonNull(sessionCache).getCurrentFolderId();
        String word = sessionCache.getWord();

        List<WordSuggestionDto> suggestions = sessionCache.getWordSuggestions();
        if (isNumeric(text)) {
            int suggestionIndex = Integer.parseInt(text);

            if (isInvalidSuggestionIndex(suggestionIndex, suggestions.size())) {
                return messageIncorrectNumber;
            }

            WordSuggestionDto chosenSuggestion = suggestions.get(suggestionIndex - 1);
            addWordToService(folderIds, chosenSuggestion.getPhrase(), chosenSuggestion.getTranslation(), userId);
            cache.evict(user.getId());

            return AddWordResponseEnum.TRANSLATION.getMessage();
        }

        if (wordService.existsSameWord(folderIds, word, text, userId)) {
            return messageTranslationExists;
        }

        kafkaProducer.sendMessage(sessionCache.getWord(), text);
        addWordToService(folderIds, sessionCache.getWord(), text, userId);
        cache.evict(user.getId());

        return AddWordResponseEnum.TRANSLATION.getMessage();
    }

    private SessionCache getSessionCache(Long userId) {
        return cache.get(userId, SessionCache.class);
    }

    private void addWordToService(Long folderIds, String phrase, String translation, Long userId) {
        wordService.addWord(folderIds, phrase, translation, userId);
    }

    private boolean isNumeric(String text) {
        return text != null && text.matches("-?\\d+");
    }

    private boolean isInvalidSuggestionIndex(int index, int size) {
        return index < 1 || index > size;
    }

}
