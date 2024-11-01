package org.annill.linguabot.actions.commands;

import org.annill.linguabot.actions.abs.AddActionHandler;
import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.states.addWordStates.GetFolderNameState;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class AddWordActionHandler extends AddActionHandler {

    public AddWordActionHandler(Cache cache, GetFolderNameState getFolderNameState) {
        super(cache, getFolderNameState);
    }

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_WORD;
    }
}
