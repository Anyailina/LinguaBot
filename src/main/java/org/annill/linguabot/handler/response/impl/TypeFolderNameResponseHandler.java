package org.annill.linguabot.handler.response.impl;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.ResponseEnum;
import org.annill.linguabot.handler.response.ResponseHandler;
import org.annill.linguabot.service.FolderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@AllArgsConstructor
@Component
public class TypeFolderNameResponseHandler implements ResponseHandler {

    private final FolderService folderService;

    @Override
    public ResponseEnum getType() {
        return ResponseEnum.TYPE_FOLDER_NAME;
    }

    @Override
    public String process(String text, User user) {
        folderService.addFolder(text, user.getId());
        return "";
    }
}
