package org.annill.linguabot.handler.response.impl.addWordStates;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.enums.response.impl.AddWordResponseEnum;
import org.annill.linguabot.handler.response.ResponseHandler;
import org.annill.linguabot.model.cache.SessionCache;
import org.annill.linguabot.model.dto.WordDto;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.service.WordService;
import org.annill.linguabot.service.WordSuggestionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class WordState implements ResponseHandler {
    private final WordService wordService;
    private final WordSuggestionService wordSuggestionService;
    private final Cache cache;
    @Value("${message.mistake.word-suggestion-exists}")
    private String messageWordSuggestionExists;


    @Override
    public ResponseEnum getType() {
        return AddWordResponseEnum.WORD;
    }

    @Override
    public String process(String word, User user) {
        Long userId = user.getId();
        Long folderId = getCurrentFolderId(userId);

        List<WordSuggestionDto> suggestions = getFilteredSuggestions(word, folderId, userId);

        String responseMessage = suggestions.isEmpty()
                ? AddWordResponseEnum.WORD.getMessage()
                : createSuggestionMessage(suggestions);

        cache.put(userId, createSessionCache(folderId, word, suggestions));

        return responseMessage;
    }

    private Long getCurrentFolderId(Long userId) {
        return Optional.ofNullable(cache.get(userId, SessionCache.class))
                .map(SessionCache::getCurrentFolderId)
                .orElse(null);
    }

    private List<WordSuggestionDto> getFilteredSuggestions(String word, Long folderId, Long userId) {
        List<WordSuggestionDto> suggestions = new ArrayList<>(wordSuggestionService.getWords(word));
        List<WordSuggestionDto> savedSuggestions = mapToSuggestionDto(wordService.getWords(folderId, word, userId));
        suggestions.removeAll(savedSuggestions);
        return suggestions;
    }

    private List<WordSuggestionDto> mapToSuggestionDto(List<WordDto> words) {
        return words.stream()
                .map(word -> new WordSuggestionDto(word.getName(), word.getTranslation()))
                .collect(Collectors.toList());
    }

    private SessionCache createSessionCache(Long folderId, String word, List<WordSuggestionDto> suggestions) {
        return suggestions.isEmpty()
                ? new SessionCache(AddWordResponseEnum.TRANSLATION, folderId, word)
                : new SessionCache(AddWordResponseEnum.SUGGEST_TRANSLATION, folderId, word, suggestions);
    }

    private String createSuggestionMessage(List<WordSuggestionDto> suggestions) {
        StringBuilder answer = new StringBuilder(messageWordSuggestionExists).append("\n");
        for (int i = 0; i < suggestions.size(); i++) {
            answer.append(i + 1).append(". ").append(suggestions.get(i).getTranslation()).append("\n");
        }
        return answer.toString();
    }
}
