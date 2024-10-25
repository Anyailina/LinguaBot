package org.annill.linguabot.fabricOfAction;

import java.util.HashMap;

public enum ActionEnum {
    ADD_FOLDER("добавить папку"),
    ADD_WORD("добавить слово");

    private final String commandText;
    private static HashMap<String,ActionEnum> map = new HashMap<>();

    ActionEnum(String commandText) {
        this.commandText = commandText;
    }
    static {
        for(ActionEnum value : values()){
            map.put(value.commandText,value);
        }
    }

    public static ActionEnum fromText(String value) {
        return map.get(value);
    }
}