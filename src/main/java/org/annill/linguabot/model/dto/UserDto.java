package org.annill.linguabot.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotEmpty
    private Long chatId;
    private String firstName;
    private String userName;
}
