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

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    private final FolderConverter folderConverter;
    private final WordConverter wordConverter;
    private final FolderService folderService;


    public WordDto addWord(String folderName, String phrase, String translation, Long userId) {
        FolderDto folderDto = folderService.getFolderByName(folderName, userId);
        if (folderDto == null) {
            return null;
        }
        Folder folder = folderConverter.convert(folderDto);
        Word word = new Word(phrase, translation, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), folder);
        return wordConverter.convert(wordRepository.save(word));
    }

    public List<WordDto> getWords(String folderName, String phrase, Long userId) {
        FolderDto folderDto = folderService.getFolderByName(folderName, userId);
        Folder folder = folderConverter.convert(folderDto);
        List<WordDto> words = wordRepository.findByNameAndFolder(phrase, folder)
                .stream()
                .map(wordConverter::convert)
                .toList();

        return words;
    }

    public Boolean wordIsSame(String folderName, String phrase, String translation, Long userId) {
        FolderDto folderDto = folderService.getFolderByName(folderName, userId);
        Folder folder = folderConverter.convert(folderDto);
        List<Word> words = wordRepository.findByNameAndTranslationAndFolder(phrase, translation, folder);

        return !words.isEmpty();
    }
}
