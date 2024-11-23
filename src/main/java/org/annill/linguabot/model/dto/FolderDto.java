package org.annill.linguabot.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class FolderDto {
    private Long id;
    @NotEmpty
    private String name;
    private UserDto userDto;
}
