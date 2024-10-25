package org.annill.linguabot.converter;

import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.Word;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FolderConverter {
    private UserConvertor userConvertor;

    public FolderDto convert(Folder folder) {
        List<Long> wordsId = folder
                .getWords()
                .stream()
                .map(Word::getId)
                .toList();
        return new FolderDto(folder.getId(), folder.getName(), wordsId, userConvertor.convert(folder.getUser()));
    }

    public Folder convert(FolderDto folderDto) {
        return new Folder(folderDto.getId(), folderDto.getName(), userConvertor.convert(folderDto.getUserDto()));
    }
}
