package org.annill.linguabot.fabricOfAction.commands;

import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.fabricOfAction.abs.BaseActionHandler;
import org.annill.linguabot.states.addWordStates.GetFolderNameState;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class AddWordActionHandler extends BaseActionHandler {

    public AddWordActionHandler(CacheManager cacheManager, GetFolderNameState getFolderNameState) {
        super(cacheManager, getFolderNameState);
    }

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_WORD;
    }
}
