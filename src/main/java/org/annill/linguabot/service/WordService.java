package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.converter.FolderConverter;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.Word;
import org.annill.linguabot.repository.WordRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    private final FolderConverter folderConverter;

    public void addWord(FolderDto folderDto, String phrase, String translation) {
        Folder folder = folderConverter.convert(folderDto);
        Word word = new Word(phrase, translation, folder);
        wordRepository.save(word);
    }
}
