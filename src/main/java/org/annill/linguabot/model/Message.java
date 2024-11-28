package org.annill.linguabot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Message {
    @Value("${message.notification}")
    private String notification;

    @Value("${message.open-miniapp}")
    private String openMiniapp;

    @Value("${message.button-open-miniapp}")
    private String buttonOpenMiniapp;

    @Value("${message.not-correct-input}")
    private String notCorrectInput;
    @Value("${message.default}")
    private String defaultMessage;

    @Value("${message.not-correct-input-with-numbers}")
    private String notCorrectInputWithNumbers;


    @Value("${message.mistake.folder-exists}")
    private String folderExists;

    @Value("${message.mistake.folder-not-exists}")
    private String folderNotExists;

    @Value("${message.mistake.translate-exists}")
    private String translateExists;

    @Value("${message.mistake.word-suggestion-exists}")
    private String wordSuggestionExists;

    @Value("${message.mistake.incorrect-number}")
    private String incorrectNumber;
}