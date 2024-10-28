package org.annill.linguabot.fabricOfAction.abs;

import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Optional;

public abstract class AddActionHandler implements ActionHandler {
    protected AddContext addContext;
    protected Cache cache;
    protected IAdd initialState;

    public AddActionHandler(CacheManager cacheManager, IAdd initialState) {
        this.cache = cacheManager.getCache("commands");
        this.addContext = new AddContext(initialState, this, cache);
        this.initialState = initialState;
    }

    @Override
    public String process(String text, long chatId, IAdd iAdd) {
        IAdd currentIAdd = Optional.ofNullable(iAdd).orElse(initialState);
        addContext.setIAdd(currentIAdd);
        ResultStatusEnum resultStatusEnum = addContext.processMessage(text, chatId);

        if (resultStatusEnum == ResultStatusEnum.RIGHT) {
            currentIAdd.nextState(addContext, text, chatId);
            return currentIAdd.getStatus();
        }
        return currentIAdd.wrongAnswer();
    }
}