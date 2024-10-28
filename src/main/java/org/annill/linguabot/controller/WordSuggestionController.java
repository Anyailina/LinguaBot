package org.annill.linguabot.controller;

import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.service.WordSuggestionService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class WordSuggestionController {
    private WordSuggestionService wordSuggestionService;

    public WordSuggestionDto addWord(String phrase, String translation) {
        return wordSuggestionService.addWord(phrase, translation);
    }

    public WordSuggestionDto getWord(String phrase) {
        return wordSuggestionService.getWord(phrase);
    }

    public List<WordSuggestionDto> getWords(String folderName) {
        return wordSuggestionService.getWords(folderName);
    }
}
