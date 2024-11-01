package org.annill.linguabot.actions.abs;

import org.annill.linguabot.actions.abs.impl.ActionHandler;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.cache.Cache;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

public abstract class AddActionHandler implements ActionHandler {
    protected AddContext addContext;
    protected Cache cache;
    protected IAdd initialState;

    public AddActionHandler(Cache cache, IAdd initialState) {
        this.cache = cache;
        this.initialState = initialState;
        addContext = new AddContext(initialState, this, cache);
    }

    @Override
    public String process(String text, User user, IAdd iAdd) {
        IAdd currentIAdd = Optional.ofNullable(iAdd).orElse(initialState);
        addContext.setIAdd(currentIAdd);
        ResultStatusEnum resultStatusEnum = addContext.processMessage(text, user.getId());

        if (resultStatusEnum == ResultStatusEnum.RIGHT) {
            currentIAdd.nextState(addContext, text, user.getId());
            return currentIAdd.getStatus();
        }
        return currentIAdd.wrongAnswer();
    }
}