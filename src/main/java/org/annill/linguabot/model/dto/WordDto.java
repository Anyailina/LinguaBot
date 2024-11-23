package org.annill.linguabot.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String translation;
    private Boolean isLearned;
    private Integer quantityRepeat;
    private Boolean isSelected;
    private FolderDto folderDto;
}
