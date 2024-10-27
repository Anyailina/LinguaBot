package org.annill.linguabot.states.context;


import lombok.Getter;
import lombok.Setter;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.cache.Cache;

@Setter
@Getter
public class AddContext {
    private IAdd iAdd;
    private ActionHandler actionHandler;
    private Cache cache;


    public AddContext(IAdd iAdd, ActionHandler actionHandler,Cache cache) {
        this.iAdd = iAdd;
        this.actionHandler = actionHandler;
        this.cache = cache;
    }

    public ResultStatusEnum processMessage(String text, long chatId) {
        return iAdd.processMessage(this,text,chatId);
    }
}
