package org.annill.linguabot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class WordSuggestionDto {
    private Long id;
    private String phrase;
    private String translation;
}
