package org.annill.linguabot.fabricOfAction.commands;

import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.annill.linguabot.states.addWordStates.GetFolderNameState;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddWordActionHandler implements ActionHandler {
    private AddContext addContext;
    private Cache cache;
    private GetFolderNameState getFolderNameState;

    public AddWordActionHandler(CacheManager cacheManager, GetFolderNameState nameFolderState) {
        cache = cacheManager.getCache("commands");
        addContext  = new AddContext(nameFolderState,this,cache);
        this.getFolderNameState = nameFolderState;
    }

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_WORD;
    }

    @Override
    public String process(String text, long chatId, IAdd iAdd) {
        IAdd currentIAdd = Optional.ofNullable(iAdd).orElse(getFolderNameState);
        addContext.setIAdd(currentIAdd);
        ResultStatusEnum resultStatusEnum = addContext.processMessage(text, chatId);

        if (resultStatusEnum == ResultStatusEnum.RIGHT) {
            currentIAdd.nextState(addContext, text, chatId);
            return currentIAdd.getStatus();
        }

        return currentIAdd.wrongAnswer();
    }

}
