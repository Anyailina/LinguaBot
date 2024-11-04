package org.annill.linguabot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WordSuggestionDto implements Serializable {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String phrase;
    private String translation;

    public WordSuggestionDto(String phrase, String translation) {
        this.phrase = phrase;
        this.translation = translation;
    }
}
