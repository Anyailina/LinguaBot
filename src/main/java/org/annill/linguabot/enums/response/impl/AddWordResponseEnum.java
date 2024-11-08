package org.annill.linguabot.enums.response.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.annill.linguabot.enums.response.ResponseEnum;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum AddWordResponseEnum implements ResponseEnum {
    GET_NAME_FOLDER("Выберите имя папки"),
    NAME_FOLDER("Напишите слово"),
    WORD("Напишите перевод"),
    SUGGEST_TRANSLATION("Слово добавлено"),
    TRANSLATION("Слово добавлено");
    private static final HashMap<String, AddWordResponseEnum> map = new HashMap<>();

    static {
        for (AddWordResponseEnum addWordStateResponseEnum : values()) {
            map.put(addWordStateResponseEnum.message, addWordStateResponseEnum);
        }
    }

    private final String message;


}
