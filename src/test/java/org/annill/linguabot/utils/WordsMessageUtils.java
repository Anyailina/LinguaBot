package org.annill.linguabot.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WordsMessageUtils {
    private final String nameFolder = "Животные";
    private final String word = "Cat";
    private final String translation = "Кот";
    private final String anotherTranslation = "Котенок";
    private final String wordSuggestion = "Cat";
    private final String translationSuggestion = "Кошечка";
    private final String messageSendWord = "Напишите слово";
    private final String messageSendTranslation = "Напишите перевод";
    private final String messageWordAdded = "Слово добавлено";
    private final String messageFolderAdded = "Папка добавлена";
    @Value("${message.name-folder}")
    private String messageNameFolder;
    @Value("${message.mistake.folder-exists}")
    private String messageFolderExits;
    @Value("${message.mistake.folder-not-exists}")
    private String messageFolderNotExits;
    @Value("${message.mistake.word-suggestion-exists}")
    private String messageWordSuggestionExists;
    @Value("${message.mistake.incorrect-number}")
    private String messageIncorrectNumber;
    @Value("${message.mistake.translate-exists}")
    private String messageTranslationExists;

}
