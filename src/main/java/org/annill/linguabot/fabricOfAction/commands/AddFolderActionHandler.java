package org.annill.linguabot.fabricOfAction.commands;

import lombok.AllArgsConstructor;
import org.annill.linguabot.controller.FolderController;
import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class AddFolderActionHandler implements ActionHandler {
    @Value("${message.name-folder}")
    private String messageNameFolder;
    @Value("${message.added-folder}")
    private String messageAddedFolder;
    private FolderController folderController;

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_FOLDER;
    }

    @Override
    public String waitProcess() {
        return messageNameFolder;
    }

    @Override
    public String process(String text, long chatId) {
        folderController.addFolder(text, chatId);
        return messageAddedFolder;
    }

}
