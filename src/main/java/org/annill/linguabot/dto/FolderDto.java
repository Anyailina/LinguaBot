package org.annill.linguabot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class FolderDto {
    private Long id;
    @NotEmpty
    private String name;
    @JsonIgnore
    private Set<Long> wordIds;
}
