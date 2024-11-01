package org.annill.linguabot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@EqualsAndHashCode
public class WordSuggestionDto implements Serializable {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String phrase;
    private String translation;
}
