package org.annill.linguabot.pattern;

public class RegexPattern {
    public static final String REGEX_PATTERN = "^[а-яА-Яa-zA-Z/_]+$";

    public static boolean isCorrectMessage(String text) {
        return text.matches(REGEX_PATTERN);
    }
}
