package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.controller.FolderController;
import org.annill.linguabot.converter.FolderConverter;
import org.annill.linguabot.converter.WordConverter;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.dto.WordDto;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.Word;
import org.annill.linguabot.repository.WordRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    private final FolderConverter folderConverter;
    private final WordConverter wordConverter;
    private final FolderController folderController;


    public WordDto addWord(String folderName, String phrase, String translation,Long userId) {
        FolderDto folderDto = folderController.getFolderByName(folderName,userId);
        if (folderDto != null) {
            Folder folder = folderConverter.convert(folderDto);
            Word word = new Word(phrase, translation, folder);
            return wordConverter.convert(wordRepository.save(word));
        }
        return null;
    }

    public WordDto getWord(String folderName, String phrase,Long userId) {
        FolderDto folderDto = folderController.getFolderByName(folderName,userId);
        Folder folder = folderConverter.convert(folderDto);

        return wordRepository.findByNameAndFolder(phrase, folder)
                .map(wordConverter::convert)
                .orElse(null);
    }
}
