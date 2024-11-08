package org.annill.linguabot.enums.response.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.annill.linguabot.enums.response.ResponseEnum;

@Getter
@AllArgsConstructor
public enum DefaultEnum implements ResponseEnum {
    DEFAULT("Неизвестная команда");
    private final String message;

}
