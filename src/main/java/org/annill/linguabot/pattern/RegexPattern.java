package org.annill.linguabot.pattern;

public class RegexPattern {
    public static final String REGEX_PATTERN_LETTERS = "[A-Za-zА-Яа-яЁё]+";
    public static final String REGEX_PATTERN_LETTERS_NUMBERS = "[A-Za-zА-Яа-яЁё0-9]+";


    public static boolean isMessageContainsOnlyLetters(String text) {
        return text.matches(REGEX_PATTERN_LETTERS);
    }
    public static boolean isMessageContainsLettersAndNumbers(String text) {
        return text.matches(REGEX_PATTERN_LETTERS_NUMBERS);
    }
}
