package org.annill.linguabot.service;


import lombok.AllArgsConstructor;
import org.annill.linguabot.converter.FolderConverter;
import org.annill.linguabot.converter.UserConvertor;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.User;
import org.annill.linguabot.repository.FolderRepository;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;
    private final FolderConverter folderConverter;
    private final UserConvertor userConvertor;
    private final UserService userService;

    public FolderDto addFolder(String name, Long userChatId) {
        if (getFolderByName(name, userChatId) != null) {
            return null;
        }

        UserDto userDto = userService.getUserIdByChatId(userChatId);
        User user = userConvertor.convert(userDto);
        Folder folder = new Folder(name, user);
        Folder savedFolder = folderRepository.save(folder);
        return folderConverter.convert(savedFolder);
    }


    public void deleteFolder(Long id) {
        folderRepository.deleteById(id);
    }

    public FolderDto getFolderByName(String name, Long userId) {
        return folderRepository.findByNameAndUserChatId(name, userId)
                .map(folderConverter::convert)
                .orElse(null);
    }
}
