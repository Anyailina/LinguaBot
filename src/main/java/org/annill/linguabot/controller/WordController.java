package org.annill.linguabot.controller;

import jakarta.validation.Valid;
import org.annill.linguabot.dto.WordDto;
import org.annill.linguabot.service.WordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/word"))
public class WordController {
    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @CrossOrigin
    @PostMapping("/{folderId}")
    public void addWord(@RequestBody @Valid WordDto word, @PathVariable("folderId") Long id) {
        wordService.addWord(word, id);
    }

    @CrossOrigin
    @GetMapping("/{folderId}")
    public List<WordDto> getWords(@PathVariable Long folderId) {
        return wordService.getWords(folderId);

    }

    @CrossOrigin
    @DeleteMapping("/{folderId}/{wordId}")
    public void deleteWord(@PathVariable("folderId") Long folderId, @PathVariable("wordId") Long wordId) {
        wordService.deleteWord(folderId, wordId);
    }
}
