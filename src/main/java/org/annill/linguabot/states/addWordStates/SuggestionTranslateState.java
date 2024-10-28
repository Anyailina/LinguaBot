package org.annill.linguabot.states.addWordStates;

import lombok.RequiredArgsConstructor;
import org.annill.linguabot.cashe.UserCacheData;
import org.annill.linguabot.cashe.WordCash;
import org.annill.linguabot.controller.WordController;
import org.annill.linguabot.controller.WordSuggestionController;
import org.annill.linguabot.enums.AddWordStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SuggestionTranslateState implements IAdd {
    private final WordController wordController;

    @Value("${message.mistake.incorrect-number}")
    private String messageIncorrectNumber;
    @Value("${message.mistake.translate-exists}")
    private String messageTranslationExists;
    private String answer;

    @Override
    public String getStatus() {
        return AddWordStateEnum.TRANSLATION.getStatesName();
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String text, long chatId) {
        UserCacheData userCacheData = addContext.getExistingUserCacheData(chatId);
        WordCash wordCash = userCacheData.getWordCash();
        List<WordSuggestionDto> wordSuggestionDtoList = userCacheData.getWordSuggestions();

        String answer;

        if (isNumeric(text)) {
            int numberOfSuggestion = Integer.parseInt(text);

            if (isInvalidSuggestionIndex(numberOfSuggestion, wordSuggestionDtoList.size())) {
                answer = messageIncorrectNumber;
                setWrongAnswer(answer);
                return ResultStatusEnum.MISTAKE;
            }

            WordSuggestionDto currentWord = wordSuggestionDtoList.get(numberOfSuggestion - 1);
            wordController.addWord(wordCash.getFolderName(), currentWord.getPhrase(), currentWord.getTranslation(), chatId);
            return ResultStatusEnum.RIGHT;
        }

        if (wordController.wordIsSame(wordCash.getFolderName(), wordCash.getWord(), chatId, text)) {
            answer = messageTranslationExists;
            setWrongAnswer(answer);
            return ResultStatusEnum.MISTAKE;
        }

        wordController.addWord(wordCash.getFolderName(), wordCash.getWord(), text, chatId);
        return ResultStatusEnum.RIGHT;
    }

    @Override
    public void nextState(AddContext addContext, String text, long chatId) {
        Cache cache = addContext.getCache();
        cache.evict(chatId);
    }

    @Override
    public String wrongAnswer() {
        return answer;
    }

    private void setWrongAnswer(String answerMessage) {
        this.answer = answerMessage;
    }

    private boolean isNumeric(String text) {
        return text != null && text.matches("\\d+");
    }

    private boolean isInvalidSuggestionIndex(int index, int size) {
        return index < 1 || index > size;
    }
}
