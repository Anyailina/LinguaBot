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
        Optional<FolderDto> folderDto = folderService.getFolderByUserChatId(folderId, chatId);
        if (folderDto.isEmpty() && !existsSameWord(folderId, phrase, translation, chatId)) {
            return;
        }
        Folder folder = folderConverter.convert(folderDto.get());
        Word word = new Word(phrase, translation, false, 0, false, folder);
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
        List<Word> optionalWord = wordRepository.findByIsLearnedAndQuantityRepeatAndIsSelectedAndFolderIdInAndFolderUserId(
                false, 0, false, folderIdList, userId
        );
        List<WordDto> wordDtoList = optionalWord.stream().map(word -> {
            word.setIsSelected(true);
            wordRepository.save(word);
            return wordConverter.convert(word);
        }).toList();

        int quantityWords = Integer.parseInt(quantityNewWord);
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
        Optional<FolderDto> folderDto = folderService.getFolderByUserChatId(folderId, chatId);
        if (folderDto.isEmpty()) {
            return null;
        }
        Folder folder = folderConverter.convert(folderDto.get());
        Optional<Word> word = wordRepository.findFirstByNameAndFolderAndTranslation(phrase, folder, translation);
        return word.isPresent();
    }


    private Word updateWord(Word updateWord, WordDto wordDto) {
        updateWord.setIsLearned(wordDto.getIsLearned());
        updateWord.setIsSelected(false);
        updateWord = repeatWordService.updateRepeatWord(updateWord, wordDto.getQuantityRepeat());
        return updateWord;
    }
}
