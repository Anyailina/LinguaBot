package org.annill.linguabot.handler.response.impl.addWordStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.enums.response.impl.AddWordResponseEnum;
import org.annill.linguabot.handler.response.ResponseHandler;
import org.annill.linguabot.model.cache.SessionCache;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.pattern.RegexPattern;
import org.annill.linguabot.service.FolderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@AllArgsConstructor
public class FolderNameState implements ResponseHandler {
    private final FolderService folderService;
    private final Cache cache;
    @Value("${message.mistake.folder-not-exists}")
    private String messageFolderNotExists;
    @Value("${message.not_correct-input}")
    private String messageInputNotCorrect;

    @Override
    public ResponseEnum getType() {
        return AddWordResponseEnum.NAME_FOLDER;
    }

    @Override
    public String process(String folderName, User user) {
        if (!RegexPattern.isMessageContainsOnlyLetters(folderName)) {
            return messageInputNotCorrect;
        }
        Long userId = user.getId();
        FolderDto folderDto = folderService.getFolderByName(folderName, userId);
        if (folderDto == null) {
            return messageFolderNotExists;
        }
        SessionCache newSessionCache = new SessionCache(AddWordResponseEnum.WORD, folderDto.getId());
        cache.put(userId, newSessionCache);

        return AddWordResponseEnum.NAME_FOLDER.getMessage();
    }

}