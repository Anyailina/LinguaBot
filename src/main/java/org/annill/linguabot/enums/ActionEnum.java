package org.annill.linguabot.enums;

import java.util.HashMap;

public enum ActionEnum {
    ADD_FOLDER("/add_folder"),
    ADD_WORD("/add_word"),
    START("/start"),
    DEFAULT("Default");

    private final String commandText;
    private static HashMap<String, ActionEnum> map = new HashMap<>();

    ActionEnum(String commandText) {
        this.commandText = commandText;
    }

    static {
        for (ActionEnum value : values()) {
            map.put(value.commandText, value);
        }
    }

    public static ActionEnum fromText(String value) {
        return map.getOrDefault(value, DEFAULT);
    }
}