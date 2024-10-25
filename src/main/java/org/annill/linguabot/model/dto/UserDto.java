package org.annill.linguabot.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotEmpty
    private Long chatId;
    @JsonIgnore
    private List<Long> folderIds;

    public UserDto(Long chatId) {
        this.chatId = chatId;
    }
}
