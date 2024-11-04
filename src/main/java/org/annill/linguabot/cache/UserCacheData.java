package org.annill.linguabot.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.annill.linguabot.actions.abs.impl.ActionHandler;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.states.impl.IAdd;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserCacheData implements Serializable {
    private IAdd addState;
    private ActionHandler actionState;
    private WordCache wordCache;
    private List<WordSuggestionDto> wordSuggestions;

    public UserCacheData(IAdd addState, ActionHandler actionState, WordCache wordCache) {
        this.addState = addState;
        this.actionState = actionState;
        this.wordCache = wordCache;
    }

    public UserCacheData(IAdd addState, ActionHandler actionState) {
        this.actionState = actionState;
        this.addState = addState;
    }
}