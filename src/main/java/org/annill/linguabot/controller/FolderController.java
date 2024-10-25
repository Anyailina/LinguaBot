package org.annill.linguabot.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.dto.UserDto;
import org.annill.linguabot.service.FolderService;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class FolderController {
    private final FolderService folderService;
    private final UserController userController;

    public void addFolder(@Valid String name, Long userChatId) {
        UserDto userDto = userController.getUserIdByChatId(userChatId);
        folderService.addFolder(name, userDto);
    }

    public void deleteFolder(@Valid Long id) {
        folderService.deleteFolder(id);
    }

    public FolderDto getFolderByName(@Valid String name) {
        return folderService.getFolderByName(name);
    }

}

