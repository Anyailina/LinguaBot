package org.annill.linguabot.enums;

public enum AddWordStateEnum {
    GET_NAME_FOLDER("Напишите имя папки"),
    NAME_FOLDER("Напишите слово"),
    WORD("Напишите перевод"),
    SUGGEST_TRANSLATION("Слово добавлено"),
    TRANSLATION("Слово добавлено");

    private final String stateName;

    AddWordStateEnum(String stateName) {
        this.stateName = stateName;
    }

    public String getStatesName() {
        return stateName;
    }
}
