package org.annill.linguabot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
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

    public String getAnotherTranslation() {
        return anotherTranslation;
    }

    public String getMessageIncorrectNumber() {
        return messageIncorrectNumber;
    }

    public String getMessageTranslationExists() {
        return messageTranslationExists;
    }

    public String getMessageWordSuggestionExists() {
        return messageWordSuggestionExists;
    }

    public String getWordSuggestion() {
        return wordSuggestion;
    }

    public String getTranslationSuggestion() {
        return translationSuggestion;
    }

    public String getMessageFolderNotExits() {
        return messageFolderNotExits;
    }

    public String getMessageNameFolder() {
        return messageNameFolder;
    }

    public String getMessageFolderExits() {
        return messageFolderExits;
    }

    public String getNameFolder() {
        return nameFolder;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public String getMessageSendWord() {
        return messageSendWord;
    }

    public String getMessageSendTranslation() {
        return messageSendTranslation;
    }

    public String getMessageWordAdded() {
        return messageWordAdded;
    }

    public String getMessageFolderAdded() {
        return messageFolderAdded;
    }
}
