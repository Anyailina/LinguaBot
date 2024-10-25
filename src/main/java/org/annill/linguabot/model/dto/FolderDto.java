package org.annill.linguabot.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class FolderDto {
    private Long id;
    @NotEmpty
    private String name;
    @JsonIgnore
    private List<Long> wordIds;
    private UserDto userDto;
}
