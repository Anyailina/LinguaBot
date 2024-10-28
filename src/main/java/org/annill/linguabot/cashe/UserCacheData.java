package org.annill.linguabot.cashe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.states.impl.IAdd;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserCacheData {
    private IAdd addState;
    private ActionHandler actionState;
    private WordCash wordCash;
    private List<WordSuggestionDto> wordSuggestions;

    public UserCacheData(IAdd addState, ActionHandler actionState, WordCash wordCash) {
        this.addState = addState;
        this.actionState = actionState;
        this.wordCash = wordCash;
    }

    public UserCacheData(IAdd addState,ActionHandler actionState) {
        this.actionState = actionState;
        this.addState = addState;
    }
}