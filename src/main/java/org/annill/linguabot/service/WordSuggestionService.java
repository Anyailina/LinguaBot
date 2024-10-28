package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.converter.WordSuggestionConvertor;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.model.entity.WordSuggestion;
import org.annill.linguabot.repository.WordSuggestionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class WordSuggestionService {
    private WordSuggestionRepository wordSuggestionRepository;
    private WordSuggestionConvertor wordSuggestionConvertor;

    public WordSuggestionDto addWord(String phrase, String translation) {
        if (getWord(phrase) != null) {
            return null;
        }
        WordSuggestion wordSuggestion = new WordSuggestion(phrase, translation);
        WordSuggestion savedWordSuggestion = wordSuggestionRepository.save(wordSuggestion);
        return wordSuggestionConvertor.convert(savedWordSuggestion);
    }

    public WordSuggestionDto getWord(String word) {
        return wordSuggestionRepository.findFirstByPhrase(word)
                .map(wordSuggestionConvertor::convert)
                .orElse(null);
    }

    public List<WordSuggestionDto> getWords(String word) {
        return wordSuggestionRepository.findByPhrase(word)
                .map(suggestions -> suggestions.stream()
                        .map(wordSuggestionConvertor::convert)
                        .toList())
                .orElse(Collections.emptyList());
    }
}
