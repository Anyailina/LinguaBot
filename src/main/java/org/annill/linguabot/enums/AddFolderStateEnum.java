package org.annill.linguabot.enums;

public enum AddFolderStateEnum {
    NAME_FOLDER("Напишите имя папки"),
    ADD_FOLDER("Папка добавлена");

    private final String stateName;

    AddFolderStateEnum(String stateName) {
        this.stateName = stateName;
    }

    public String getStatesName() {
        return stateName;
    }
}
