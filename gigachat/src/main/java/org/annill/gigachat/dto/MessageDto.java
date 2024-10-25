package org.annill.gigachat.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.annill.gigachat.enums.Role;


@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {
    private String content;
    private Role role;
}