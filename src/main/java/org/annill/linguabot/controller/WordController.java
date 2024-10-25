package org.annill.linguabot.controller;

import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.service.WordService;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WordController {
    private final FolderController folderController;
    private final WordService wordService;

    public void addWord(String folderName, String word, String translation) {
        FolderDto folder = folderController.getFolderByName(folderName);
        System.out.println(folder.getName());
        wordService.addWord(folder, word, translation);
    }
}
