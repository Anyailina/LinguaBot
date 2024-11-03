package org.annill.linguabot.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {
    @JsonIgnore
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String translation;
    private FolderDto folderDto;
}
