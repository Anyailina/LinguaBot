package org.annill.linguabot.fabricOfAction.commands;

import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.annill.linguabot.states.addFolderStates.NameDirectoryState;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AddFolderActionHandler implements ActionHandler {
    private AddContext addContext;
    private Cache cache;
    private NameDirectoryState nameDirectoryState;

    public AddFolderActionHandler(CacheManager cacheManager, NameDirectoryState nameDirectoryState) {
        cache = cacheManager.getCache("commands");
        addContext = new AddContext(nameDirectoryState, this, cache);
        this.nameDirectoryState = nameDirectoryState;
    }

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_FOLDER;
    }


    @Override
    public String process(String text, long chatId, IAdd iAdd) {
        IAdd currentIAdd = Optional.ofNullable(iAdd).orElse(nameDirectoryState);
        addContext.setIAdd(currentIAdd);
        ResultStatusEnum resultStatusEnum = addContext.processMessage(text, chatId);

        if (resultStatusEnum == ResultStatusEnum.RIGHT) {
            currentIAdd.nextState(addContext, text, chatId);
            return currentIAdd.getStatus();
        }

        return currentIAdd.wrongAnswer();
    }

}
