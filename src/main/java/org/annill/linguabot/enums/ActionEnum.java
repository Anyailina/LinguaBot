package org.annill.linguabot.enums;

import lombok.Getter;

import java.util.HashMap;

public enum ActionEnum {
    ADD_FOLDER("/add_folder"),
    ADD_WORD("/add_word"),
    START("/start"),
    DEFAULT("Default");

    private static HashMap<String, ActionEnum> map = new HashMap<>();

    static {
        for (ActionEnum value : values()) {
            map.put(value.commandText, value);
        }
    }

    @Getter
    private final String commandText;


    ActionEnum(String commandText) {
        this.commandText = commandText;
    }

    public static ActionEnum fromText(String value) {
        return map.getOrDefault(value, DEFAULT);
    }
}