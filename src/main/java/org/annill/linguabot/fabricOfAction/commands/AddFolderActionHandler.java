package org.annill.linguabot.fabricOfAction.commands;

import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.fabricOfAction.abs.BaseActionHandler;
import org.annill.linguabot.states.addFolderStates.NameDirectoryState;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class AddFolderActionHandler extends BaseActionHandler {

    public AddFolderActionHandler(CacheManager cacheManager, NameDirectoryState nameDirectoryState) {
        super(cacheManager, nameDirectoryState);
    }

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_FOLDER;
    }
}
