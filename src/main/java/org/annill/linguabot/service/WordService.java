package org.annill.linguabot.service;

import org.annill.linguabot.converter.WordConverter;
import org.annill.linguabot.dto.WordDto;
import org.annill.linguabot.entity.Folder;
import org.annill.linguabot.entity.Word;
import org.annill.linguabot.exception.impl.IdNotCorrectException;
import org.annill.linguabot.repository.FolderRepository;
import org.annill.linguabot.repository.FolderWordRepository;
import org.annill.linguabot.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WordService {
    private final WordRepository wordRepository;
    private final WordConverter wordConverter;
    private final FolderRepository folderRepository;
    private final FolderWordRepository folderWordRepository;

    public WordService(WordRepository wordRepository, WordConverter wordConverter, FolderRepository folderRepository, FolderWordRepository folderWordRepository) {
        this.wordRepository = wordRepository;
        this.wordConverter = wordConverter;
        this.folderRepository = folderRepository;
        this.folderWordRepository = folderWordRepository;
    }

    public void addWord(WordDto wordDto, Long id) {
        Folder folder = folderRepository.findById(id).orElseThrow(IdNotCorrectException::new);
        Set<Folder> folderSet = new HashSet<>(Set.of(folder));
        Word word = wordConverter.convert(wordDto, folderSet);
        wordRepository.save(word);
    }

    public void deleteWord(Long folderId, Long wordId) {
        folderWordRepository.deleteByWordId(folderId, wordId);
    }


    public List<WordDto> getWords(Long folderId) {
        List<Word> words = wordRepository.findAllWord(folderId);
        return words.stream()
                .map(wordConverter::convert)
                .collect(Collectors.toList());
    }
}
