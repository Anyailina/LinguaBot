package org.annill.linguabot.controller;

import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.service.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/folder")
public class FolderController {
    private final FolderService folderService;

    @GetMapping("/user/{id}")
    @CrossOrigin
    public ResponseEntity<List<FolderDto>> getFolderByUserId(@PathVariable Long id) {
        List<FolderDto> folders = folderService.getFolders(id);
        return ResponseEntity.ok(folders);
    }
}
