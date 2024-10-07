package org.annill.linguabot.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FolderWordDto {
    private Long id;
    @NotNull
    private Long folderId;
    @NotNull
    private Long wordId;
}
