package org.annill.linguabot.controller;

import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.WordDto;
import org.annill.linguabot.service.WordService;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WordController {
    private final WordService wordService;

    public WordDto addWord(String folderName, String word, String translation,Long userId) {
        return wordService.addWord(folderName, word, translation,userId);
    }

    public WordDto getWord(String folderName, String word, Long userId) {
        return wordService.getWord(folderName, word,userId);
    }
    public Boolean wordIsSame(String folderName, String word, Long userId,String translation) {
        return wordService.wordIsSame(folderName, word,translation,userId);
    }
}
