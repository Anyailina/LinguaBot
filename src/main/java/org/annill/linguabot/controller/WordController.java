package org.annill.linguabot.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.annill.linguabot.model.dto.WordDto;
import org.annill.linguabot.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/word")
public class WordController {
    private final WordService wordService;

    @SneakyThrows
    @PostMapping("/user/{id}")
    public ResponseEntity<List<WordDto>> getNewWordsByFolderByUserId(@PathVariable Long id, @RequestBody List<Long> folderIds) {
        List<WordDto> wordDto = wordService.getNewWords(folderIds, id);
        return ResponseEntity.ofNullable(wordDto);
    }

    @PutMapping
    public void changeWordsLearnedByUserId(@RequestBody List<WordDto> words) {
        wordService.updateWords(words);
    }

    @PostMapping("/repeat/user/{id}")
    public ResponseEntity<List<WordDto>> getRepeatWordsByFolderByUserId(@PathVariable Long id, @RequestBody List<Long> folderIds) {
        List<WordDto> wordDtoList = wordService.getWordsForRepeat(folderIds, id);
        return ResponseEntity.ok(wordDtoList);
    }
}
