package org.annill.linguabot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PageEnum {
    NEXT_PAGE("nextPage"),
    PREVIOUS_PAGE("previousPage");
    private final String message;
}
