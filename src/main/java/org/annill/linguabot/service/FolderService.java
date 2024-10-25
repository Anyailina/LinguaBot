package org.annill.linguabot.service;


import lombok.RequiredArgsConstructor;
import org.annill.linguabot.converter.FolderConverter;
import org.annill.linguabot.converter.UserConvertor;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.User;
import org.annill.linguabot.repository.FolderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;
    private final FolderConverter folderConverter;
    private final UserConvertor userConvertor;

    public void addFolder(String name, UserDto userDto) {
        User user = userConvertor.convert(userDto);
        Folder folder = new Folder(name, user);
        folderRepository.save(folder);
    }


    public void deleteFolder(Long id) {
        folderRepository.deleteById(id);
    }

    public FolderDto getFolderByName(String name) {
        Folder folder = folderRepository.getFolderByName(name);
        return folderConverter.convert(folder);
    }
}
