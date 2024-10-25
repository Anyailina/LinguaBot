package org.annill.gigachat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.annill.gigachat.enums.Model;

import java.util.List;

@AllArgsConstructor
@Getter
public class RequestAiDto {
    private Model model;
    private List<MessageDto> messages;
    private boolean stream;

    @JsonProperty(value = "update_interval")
    private int updateInterval;
}