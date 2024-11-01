package org.annill.linguabot.actions.commands;


import org.annill.linguabot.actions.abs.AddActionHandler;
import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.states.addFolderStates.NameDirectoryState;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class AddFolderActionHandler extends AddActionHandler {

    public AddFolderActionHandler(Cache cache, NameDirectoryState nameDirectoryState) {
        super(cache, nameDirectoryState);
    }

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_FOLDER;
    }
}
