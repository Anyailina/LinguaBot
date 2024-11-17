package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import org.annill.linguabot.converter.FolderConverter;
import org.annill.linguabot.converter.WordConverter;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.dto.WordDto;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.Word;
import org.annill.linguabot.repository.WordRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    private final FolderConverter folderConverter;
    private final WordConverter wordConverter;
    private final FolderService folderService;
    private final RepeatWordService repeatWordService;
    @Value("${quantity-word}")
    private String quantityNewWord;


    public void addWord(Long folderId, String phrase, String translation, Long chatId) {
        FolderDto folderDto = folderService.getFolderByUserChatId(folderId, chatId);
        if (folderDto == null && !existsSameWord(folderId, phrase, translation, chatId)) {
            return;
        }
        Folder folder = folderConverter.convert(folderDto);
        Word word = new Word(phrase, translation, false, 0, folder);
        wordRepository.save(word);
    }

    public void updateWords(List<WordDto> words) {
        List<Word> wordList = words.stream()
                .map(wordDto -> wordRepository.findById(wordDto.getId())
                        .map(word -> updateWord(word, wordDto))
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
        wordRepository.saveAll(wordList);
    }

    public List<WordDto> getWords(Long folderId, String phrase, Long chatId) {
        return wordRepository.findWordsByNameFolderIdAndUserChatId(phrase, folderId, chatId)
                .stream()
                .map(wordConverter::convert)
                .toList();
    }

    public List<WordDto> getNewWords(List<Long> folderIdList, Long userId) {
        int quantityWords = Integer.parseInt(quantityNewWord);
        List<WordDto> wordDtoList = getWordsByLearnedByFolderList(false, 0, folderIdList, userId, quantityWords);
        if (quantityWords >= wordDtoList.size()) {
            return wordDtoList;
        }
        return wordDtoList.subList(0, quantityWords);
    }


    public List<WordDto> getWordsForRepeat(List<Long> folderIdList, Long userId) {
        List<Word> wordList = wordRepository.findByIsLearnedAndQuantityRepeatNotAndFolderIdInAndFolderUserId(false, 0, folderIdList, userId);
        List<Word> repeatWordList = repeatWordService.checkIfNeedRepeat(wordList);
        return repeatWordList.stream()
                .map(wordConverter::convert)
                .toList();
    }

    public Boolean existsSameWord(Long folderId, String phrase, String translation, Long chatId) {
        FolderDto folderDto = folderService.getFolderByUserChatId(folderId, chatId);
        Folder folder = folderConverter.convert(folderDto);
        Optional<Word> word = wordRepository.findFirstByNameAndFolderAndTranslation(phrase, folder, translation);
        return word.isPresent();
    }

    private List<WordDto> getWordsByLearnedByFolderList(Boolean isLearned, Integer quantityRepeat, List<Long> folderIdList, Long userId, int quantityWords) {
        return wordRepository.findByIsLearnedAndQuantityRepeatAndFolderIdInAndFolderUserId(isLearned, quantityRepeat, folderIdList, userId).stream()
                .map(wordConverter::convert)
                .toList();
    }

    private Word updateWord(Word updateWord, WordDto wordDto) {
        updateWord.setIsLearned(wordDto.getIsLearned());
        updateWord.setUpdateAt(LocalDateTime.now());
        updateWord = repeatWordService.updateRepeatWord(updateWord, wordDto.getQuantityRepeat());
        return updateWord;
    }
}
