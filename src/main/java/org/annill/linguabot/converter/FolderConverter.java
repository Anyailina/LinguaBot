package org.annill.linguabot.converter;

import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.entity.Folder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FolderConverter {
    private UserConvertor userConvertor;

    public FolderDto convert(Folder folder) {
        return new FolderDto(folder.getId(), folder.getName(), userConvertor.convert(folder.getUser()));
    }

    public Folder convert(FolderDto folderDto) {
        return new Folder(folderDto.getId(), folderDto.getName(), userConvertor.convert(folderDto.getUserDto()));
    }
}
