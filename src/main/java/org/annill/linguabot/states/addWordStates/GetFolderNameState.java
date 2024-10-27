package org.annill.linguabot.states.addWordStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.cashe.UserCacheData;
import org.annill.linguabot.controller.FolderController;
import org.annill.linguabot.enums.AddWordStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetFolderNameState implements IAdd {
    private NameFolderState nameFolderState;
    @Override
    public String getStatus() {
        return AddWordStateEnum.GET_NAME_FOLDER.getStatesName();
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String text, long chatId) {
        return ResultStatusEnum.RIGHT;
    }

    @Override
    public void nextState(AddContext addContext, String text, long chatId) {
        UserCacheData userCacheData = new UserCacheData(nameFolderState,addContext.getActionHandler());
        addContext.getCache().put(chatId, userCacheData);
        addContext.setIAdd(nameFolderState);
    }

    @Override
    public String wrongAnswer() {
       return "";
    }


}
