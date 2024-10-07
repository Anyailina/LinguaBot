package org.annill.linguabot.service;


import org.annill.linguabot.converter.FolderConverter;
import org.annill.linguabot.dto.FolderDto;
import org.annill.linguabot.entity.Folder;
import org.annill.linguabot.repository.FolderRepository;
import org.annill.linguabot.repository.FolderWordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final FolderConverter folderConverter;
    private final FolderWordRepository folderWordRepository;

    public FolderService(FolderRepository folderRepository, FolderConverter folderConverter, FolderWordRepository folderWordRepository) {
        this.folderRepository = folderRepository;
        this.folderConverter = folderConverter;
        this.folderWordRepository = folderWordRepository;
    }

    public void addFolder(String name) {
        Folder folder = new Folder().setName(name);
        folderRepository.save(folder);
    }

    public void deleteFolder(Long id) {
        folderRepository.deleteById(id);
        folderWordRepository.deleteByFolderId(id);
    }

    public List<FolderDto> getFolders() {
        List<Folder> folders = folderRepository.findAll();
        return folders.stream()
                .map(folderConverter::convert)
                .collect(Collectors.toList());
    }
}
