package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.converter.FolderConverter;
import org.annill.linguabot.converter.WordConverter;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.dto.WordDto;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.Word;
import org.annill.linguabot.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    private final FolderConverter folderConverter;
    private final WordConverter wordConverter;
    private final FolderService folderService;


    public void addWord(Long folderId, String phrase, String translation, Long userId) {
        FolderDto folderDto = folderService.getFolderById(folderId, userId);
        if (folderDto == null && !existsSameWord(folderId, phrase, translation, userId)) {
            return;
        }
        Folder folder = folderConverter.convert(folderDto);
        Word word = new Word(phrase, translation, folder);
        wordRepository.save(word);
    }

    public List<WordDto> getWords(Long folderId, String phrase, Long userId) {
        FolderDto folderDto = folderService.getFolderById(folderId, userId);
        Folder folder = folderConverter.convert(folderDto);

        return wordRepository.findByNameAndFolder(phrase, folder)
                .stream()
                .map(wordConverter::convert)
                .toList();
    }

    public Boolean existsSameWord(Long folderId, String phrase, String translation, Long userId) {
        FolderDto folderDto = folderService.getFolderById(folderId, userId);
        Folder folder = folderConverter.convert(folderDto);
        Optional<Word> word = wordRepository.findFirstByNameAndFolderAndTranslation(phrase, folder, translation);
        return word.isPresent();
    }
}
