package org.annill.linguabot.controller;

import jakarta.validation.Valid;
import org.annill.linguabot.dto.FolderDto;
import org.annill.linguabot.service.FolderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderController {
    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @CrossOrigin
    @PostMapping("/{name}")
    public void addFolder(@PathVariable String name) {
        folderService.addFolder(name);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public void deleteFolder(@PathVariable @Valid Long id) {
        folderService.deleteFolder(id);
    }

    @GetMapping
    @CrossOrigin
    public List<FolderDto> getFolders() {
        return folderService.getFolders();
    }
}

