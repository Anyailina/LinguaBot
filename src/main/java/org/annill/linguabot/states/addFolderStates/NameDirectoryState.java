package org.annill.linguabot.states.addFolderStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.caсhe.UserCacheData;
import org.annill.linguabot.enums.AddFolderStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class NameDirectoryState implements IAdd {
    private final AddFolderState addFolderState;

    @Override
    public String getStatus() {
        return AddFolderStateEnum.NAME_FOLDER.getStatesName();
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String text, long chatId) {
        UserCacheData userCacheData = new UserCacheData(addFolderState, addContext.getActionHandler());
        addContext.getCache().put(chatId, userCacheData);
        return ResultStatusEnum.RIGHT;
    }

    @Override
    public void nextState(AddContext addContext, String text, long chatId) {
        addContext.setIAdd(addFolderState);
    }

    @Override
    public String wrongAnswer() {
        return "";
    }
}
