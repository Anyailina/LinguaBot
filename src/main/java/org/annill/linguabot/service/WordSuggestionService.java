package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.feignClient.WordSuggestionFeignClient;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WordSuggestionService {
    private final WordSuggestionFeignClient wordSuggestionFeignClient;

    public List<WordSuggestionDto> getWords(String word, String translation) {
        WordSuggestionDto wordSuggestionDto = new WordSuggestionDto(word, translation);
        return wordSuggestionFeignClient.getAnswer(wordSuggestionDto);
    }
}
