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
    @PostMapping("/user")
    public ResponseEntity<List<WordDto>> getFolderByUserId(@RequestParam Long id, @RequestBody List<Long> folderIds) {
        List<WordDto> wordDtoList = wordService.getWordsByFolderList(folderIds, id);
        return wordDtoList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(wordDtoList);
    }
}
