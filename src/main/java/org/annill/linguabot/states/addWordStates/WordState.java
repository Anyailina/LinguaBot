package org.annill.linguabot.states.addWordStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.cashe.UserCacheData;
import org.annill.linguabot.cashe.WordCash;
import org.annill.linguabot.controller.WordController;
import org.annill.linguabot.controller.WordSuggestionController;
import org.annill.linguabot.enums.AddWordStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.model.dto.WordDto;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class WordState implements IAdd {
    private final IAdd translateState;
    private final IAdd suggestionTranslateState;
    private final WordSuggestionController wordSuggestionController;
    private final WordController wordController;

    @Value("${message.mistake.word-suggestion-exists}")
    private String messageWordSuggestionExists;

    private String answer;

    @Override
    public String getStatus() {
        return answer;
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String text, long chatId) {
        return ResultStatusEnum.RIGHT;
    }

    @Override
    public String wrongAnswer() {
        return "";
    }

    @Override
    public void nextState(AddContext addContext, String text, long chatId) {
        UserCacheData userCacheData = addContext.getExistingUserCacheData(chatId);
        WordCash wordCash = createWordCash(userCacheData, text);
        List<WordSuggestionDto> wordsSuggestionDto = new ArrayList<>(wordSuggestionController.getWords(text));
        List<WordDto> savedWordsDto = wordController.getWords(wordCash.getFolderName(), text, chatId);

        List<WordSuggestionDto> savedWords = savedWordsDto.stream()
                .map(element -> new WordSuggestionDto(element.getId(), element.getName(), element.getTranslation()))
                .collect(Collectors.toCollection(ArrayList::new));

        wordsSuggestionDto.removeAll(savedWords);

        IAdd nextState = determineNextState(wordsSuggestionDto);
        answer = generateAnswer(wordsSuggestionDto);

        UserCacheData newUserCacheData = createUserCacheData(nextState, addContext, wordCash, wordsSuggestionDto);
        updateContextAndCache(addContext, nextState, chatId, newUserCacheData);
    }

    private IAdd determineNextState(List<WordSuggestionDto> wordsDto) {
        return wordsDto.isEmpty() ? translateState : suggestionTranslateState;
    }

    private String generateAnswer(List<WordSuggestionDto> wordsDto) {
        return wordsDto.isEmpty() ? AddWordStateEnum.WORD.getStatesName() : createSuggestionMessage(wordsDto);
    }

    private WordCash createWordCash(UserCacheData userCacheData, String text) {
        return new WordCash(userCacheData.getWordCash().getFolderName(), text);
    }

    private UserCacheData createUserCacheData(IAdd state, AddContext addContext, WordCash wordCash, List<WordSuggestionDto> suggestions) {
        return new UserCacheData(state, addContext.getActionHandler(), wordCash, suggestions);
    }

    private void updateContextAndCache(AddContext addContext, IAdd nextState, long chatId, UserCacheData newUserCacheData) {
        Cache cache = addContext.getCache();
        addContext.setIAdd(nextState);
        cache.put(chatId, newUserCacheData);
    }

    private String createSuggestionMessage(List<WordSuggestionDto> wordSuggestionDtoList) {
        StringBuilder answer = new StringBuilder(messageWordSuggestionExists).append("\n");
        for (int i = 0; i < wordSuggestionDtoList.size(); i++) {
            answer.append(i + 1).append(". ").append(wordSuggestionDtoList.get(i).getTranslation()).append("\n");
        }
        return answer.toString();
    }
}
