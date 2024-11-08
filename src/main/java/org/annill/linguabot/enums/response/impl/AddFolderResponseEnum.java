package org.annill.linguabot.enums.response.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.annill.linguabot.enums.response.ResponseEnum;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public enum AddFolderResponseEnum implements ResponseEnum {
    NAME_FOLDER("Напишите имя папки"),
    ADD_FOLDER("Папка добавлена"),
    EXIST_FOLDER("Папка существует");
    private static final HashMap<String, AddFolderResponseEnum> map = new HashMap<>();

    static {
        for (AddFolderResponseEnum addFolderResponseEnum : values()) {
            map.put(addFolderResponseEnum.message, addFolderResponseEnum);
        }
    }

    private final String message;
}
