package org.annill.linguabot.enums.action;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum ActionEnum {
    ADD_FOLDER("/add_folder"),
    ADD_WORD("/add_word"),
    START("/start"),
    PROCESS_REPLY("");

    private static HashMap<String, ActionEnum> map = new HashMap<>();

    static {
        for (ActionEnum value : values()) {
            map.put(value.commandText, value);
        }
    }

    private final String commandText;


    public static ActionEnum fromText(String value) {
        return map.getOrDefault(value, PROCESS_REPLY);
    }
}