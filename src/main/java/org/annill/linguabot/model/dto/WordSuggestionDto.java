package org.annill.linguabot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class WordSuggestionDto {
    private Long id;
    private String phrase;
    private String translation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordSuggestionDto that = (WordSuggestionDto) o;
        return Objects.equals(phrase, that.phrase) && Objects.equals(translation, that.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phrase, translation);
    }
}
