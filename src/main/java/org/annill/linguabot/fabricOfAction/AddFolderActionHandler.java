package org.annill.linguabot.fabricOfAction;

import org.annill.linguabot.controller.FolderController;
import org.annill.linguabot.fabricOfAction.impl.ActionHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class AddFolderActionHandler implements ActionHandler {
    private FolderController folderController;

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_FOLDER;
    }

    @Override
    public String process(Update update) {
       // folderController.addFolder();
        return null;
    }

}
