package org.annill.linguabot.converter;

import org.annill.linguabot.dto.FolderDto;
import org.annill.linguabot.entity.Folder;
import org.annill.linguabot.entity.Word;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FolderConverter {
    public FolderDto convert(Folder folder) {
        return new FolderDto()
                .setId(folder.getId())
                .setName(folder.getName())
                .setWordIds(folder
                        .getWords()
                        .stream()
                        .map(Word::getId)
                        .collect(Collectors.toSet()));
    }

    public Folder convert(FolderDto folderDto, Set<Word> words) {
        return new Folder()
                .setId(folderDto.getId())
                .setName(folderDto.getName())
                .setWords(words);
    }
}
