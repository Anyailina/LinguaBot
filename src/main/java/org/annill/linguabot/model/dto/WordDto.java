package org.annill.linguabot.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@AllArgsConstructor
public class WordDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String translation;
    private FolderDto folderDto;
}
