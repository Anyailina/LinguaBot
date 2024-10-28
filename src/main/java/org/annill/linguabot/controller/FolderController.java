package org.annill.linguabot.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.service.FolderService;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class FolderController {
    private final FolderService folderService;

    public FolderDto addFolder(@Valid String name, Long userChatId) {
         return folderService.addFolder(name, userChatId);
    }

    public void deleteFolder(@Valid Long id) {
        folderService.deleteFolder(id);
    }

    public FolderDto getFolderByName(@Valid String name, Long userId) {
        return folderService.getFolderByName(name,userId);
    }
}

