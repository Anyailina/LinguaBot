package org.annill.linguabot.handler.response.impl.addFolderStates;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.response.ResponseEnum;
import org.annill.linguabot.enums.response.impl.AddFolderResponseEnum;
import org.annill.linguabot.handler.response.ResponseHandler;
import org.annill.linguabot.model.Message;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.pattern.RegexPattern;
import org.annill.linguabot.service.FolderService;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AddFolderResponseHandler implements ResponseHandler {
    private final FolderService folderService;
    private final Cache cache;
    private final Message message;


    @Override
    public ResponseEnum getType() {
        return AddFolderResponseEnum.NAME_FOLDER;
    }

    @Override
    public String process(String text, User user) {
        if (!RegexPattern.isMessageContainsOnlyLetters(text)) {
            return message.getNotCorrectInput();
        }
        Long chatId = user.getId();
        Optional<FolderDto> folderDto = folderService.getFolderByName(text, user.getId());

        if (folderDto.isPresent()) {
            return message.getFolderExists();
        }
        folderService.addFolder(text, user.getId());
        cache.evict(chatId);
        return AddFolderResponseEnum.ADD_FOLDER.getMessage();
    }
}
