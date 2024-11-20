package org.annill.linguabot.controller;

import lombok.AllArgsConstructor;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.service.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/folder")
public class FolderController {
    private final FolderService folderService;

    //TODO: do path param {id}
    @GetMapping("/user")
    public ResponseEntity<List<FolderDto>> getFolderByUserId(@RequestParam("id") Long id) {
        List<FolderDto> folders = folderService.getFolders(id);
        return ResponseEntity.ok(folders);
    }
}
