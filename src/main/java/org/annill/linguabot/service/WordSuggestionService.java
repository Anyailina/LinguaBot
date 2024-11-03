package org.annill.linguabot.service;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class WordSuggestionService {
    private final RestTemplate wordSuggestionTemplate;
    private final Utils utils;
    @Value("${http.word.suggestion}")
    private String addressWordSuggestion;

    @SneakyThrows
    public List<WordSuggestionDto> getWords(String word, String translation) {
        WordSuggestionDto wordTranslation = new WordSuggestionDto(word, translation);
        String wordsSuggestion = wordSuggestionTemplate.postForObject(addressWordSuggestion, wordTranslation, String.class);
        List<WordSuggestionDto> wordsSuggestionDto = utils.getObjectMapper().readValue(wordsSuggestion, CollectionType.construct(List.class, SimpleType.construct(WordSuggestionDto.class)));
        return wordsSuggestionDto;

    }
}
