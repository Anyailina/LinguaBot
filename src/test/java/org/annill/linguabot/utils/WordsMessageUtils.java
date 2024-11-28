package org.annill.linguabot.utils;

import lombok.Getter;
import org.annill.linguabot.enums.response.impl.AddFolderResponseEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WordsMessageUtils {
    private final String nameFolder = "Животные";
    private final Long idFolder = 6L;
    private final String word = "Cat";
    private final String notExistsFolder = "kc";
    private final String translation = "Кот";
    private final String anotherTranslation = "Котенок";
    private final String wordSuggestion = "Cat";
    private final String translationSuggestion = "Кошечка";
    private final String messageSendWord = "Напишите слово";
    private final String messageSendTranslation = "Напишите перевод";
    private final String messageWordAdded = "Слово добавлено";
    private final String messageFolderAdded = "Папка добавлена";
    private final String messageNameFolder = AddFolderResponseEnum.NAME_FOLDER.getMessage();
    @Value("${message.mistake.folder-exists}")
    private String messageFolderExits;
    @Value("${message.mistake.folder-not-exists}")
    private String messageFolderNotExits;
    @Value("${message.mistake.word-suggestion-exists}")
    private String messageWordSuggestionExists;
    @Value("${message.not-correct-input-with-numbers}")
    private String messageIncorrectInput;
    @Value("${message.mistake.translate-exists}")
    private String messageTranslationExists;
    @Value("${message.not-exists}")
    private String messageNotExistsFolder;
    @Value("${message.not-correct-input}")
    private String messageNotCorrectInput;
}
