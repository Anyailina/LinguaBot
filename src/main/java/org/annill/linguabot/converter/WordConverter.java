package org.annill.linguabot.converter;

import org.annill.linguabot.dto.WordDto;
import org.annill.linguabot.entity.Folder;
import org.annill.linguabot.entity.Word;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WordConverter {
    public WordDto convert(Word word) {
        Set<Long> folderIds = word.getFolders().stream()
                .map(Folder::getId)
                .collect(Collectors.toSet());
        return new WordDto()
                .setId(word.getId())
                .setTranslation(word.getTranslation())
                .setName(word.getName())
                .setFolderIds(folderIds);
    }

    public Word convert(WordDto wordDto, Set<Folder> folders) {
        return new Word()
                .setId(wordDto.getId())
                .setName(wordDto.getName())
                .setTranslation(wordDto.getTranslation())
                .setFolders(folders);
    }
}
