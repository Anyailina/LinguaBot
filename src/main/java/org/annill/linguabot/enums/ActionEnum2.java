package org.annill.linguabot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum ActionEnum2 {
    ADD_FOLDER("/add_folder"),
    ADD_WORD("/add_word"),
    START("/start"),
    PROCESS_REPLY("");

    private static final HashMap<String, ActionEnum2> map = new HashMap<>();

    static {
        for (ActionEnum2 value : values()) {
            map.put(value.commandText, value);
        }
    }

    private final String commandText;

    public static ActionEnum2 fromText(String value) {
        return map.getOrDefault(value, PROCESS_REPLY);
    }

}