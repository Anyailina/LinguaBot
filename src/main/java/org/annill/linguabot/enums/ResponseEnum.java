package org.annill.linguabot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum ResponseEnum {
    TYPE_FOLDER_NAME("Напишите имя папки"),
    DEFAULT("");

    private static final HashMap<String, ResponseEnum> map = new HashMap<>();

    static {
        for (ResponseEnum responseEnum : values()) {
            map.put(responseEnum.message, responseEnum);
        }
    }

    private String message;

    public static ResponseEnum fromText(String value) {
        return map.getOrDefault(value, DEFAULT);
    }

    public ResponseEnum setMessage(String message) {
        this.message = message;
        return this;
    }
}
