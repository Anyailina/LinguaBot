package org.annill.linguabot.states.addFolderStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.controller.FolderController;
import org.annill.linguabot.enums.AddFolderStateEnum;
import org.annill.linguabot.enums.ResultStatusEnum;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.states.context.AddContext;
import org.annill.linguabot.states.impl.IAdd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddFolderState implements IAdd {
    private final FolderController folderController;
    @Value("${message.mistake.folder-exists}")
    private String messageExists;
    
    @Override
    public String getStatus() {
        return AddFolderStateEnum.ADD_FOLDER.getStatesName();
    }

    @Override
    public ResultStatusEnum processMessage(AddContext addContext, String text, long chatId) {
        FolderDto folderDto = folderController.addFolder(text,chatId);
        if (folderDto == null){
            return ResultStatusEnum.MISTAKE;
        }
        return ResultStatusEnum.RIGHT;
    }

    @Override
    public void nextState(AddContext addContext, String text, long chatId) {
        addContext.getCache().evict(chatId);
    }


    @Override
    public String wrongAnswer() {
        return messageExists;
    }
}
