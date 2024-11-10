package org.annill.linguabot.handler.response.impl.addFolderStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.enums.response.impl.AddFolderResponseEnum;
import org.annill.linguabot.handler.response.ResponseHandler;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.pattern.RegexPattern;
import org.annill.linguabot.service.FolderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@AllArgsConstructor
public class AddFolderResponseHandler implements ResponseHandler {
    private final FolderService folderService;
    private final Cache cache;
    @Value("${message.not_correct-input}")
    private String messageInputNotCorrect;
    @Value("${message.mistake.folder-exists}")
    private String messageExists;


    @Override
    public ResponseEnum getType() {
        return AddFolderResponseEnum.NAME_FOLDER;
    }

    @Override
    public String process(String text, User user) {
        if (!RegexPattern.isMessageContainsOnlyLetters(text)) {
            return messageInputNotCorrect;
        }
        Long userId = user.getId();
        FolderDto folderDto = folderService.getFolderByName(text, user.getId());

        if (folderDto != null) {
            return messageExists;
        }
        folderService.addFolder(text, user.getId());
        cache.evict(userId);
        return AddFolderResponseEnum.ADD_FOLDER.getMessage();
    }
}
