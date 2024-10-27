package org.annill.linguabot.cashe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.annill.linguabot.states.impl.IAdd;

@AllArgsConstructor
@Getter
public class UserCacheData {
    private IAdd addState;
    private ActionHandler actionState;
    private WordCash wordCash;

    public UserCacheData(IAdd addState,ActionHandler actionState) {
        this.actionState = actionState;
        this.addState = addState;
    }
}