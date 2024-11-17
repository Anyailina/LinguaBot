package org.annill.linguabot.handler.action.impl;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.enums.response.impl.AddWordResponseEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.annill.linguabot.model.cache.SessionCache;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.ui.FolderInlineKeyBoard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@AllArgsConstructor
public class AddWordActionHandler implements ActionHandler {
    private Cache cache;
    private FolderService folderService;
    private FolderInlineKeyBoard folderInlineKeyBoard;
    @Value("${folder.select}")
    private String selectFolder;
    @Value("${message.not-exists}")
    private String messageNotExists;

    @Override
    public ActionEnum getType() {
        return ActionEnum.ADD_WORD;
    }

    @Override
    public BotApiMethod<?> process(String command, Update update) {
        Long chatId = update.getMessage().getFrom().getId();
        Page<FolderDto> page = folderService.getPageFolderByUserChatId(chatId, 0, folderInlineKeyBoard.getPageSize());
        SessionCache sessionCache = new SessionCache()
                .setResponse(AddWordResponseEnum.NAME_FOLDER)
                .setCurrentPage(0);

        cache.put(chatId, sessionCache);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        InlineKeyboardMarkup inlineKeyboard = folderInlineKeyBoard.createInlineKeyboard(page);
        if (inlineKeyboard.getKeyboard().isEmpty()) {
            sendMessage.setText(messageNotExists);
        } else {
            sendMessage.setText(selectFolder);
            sendMessage.setReplyMarkup(inlineKeyboard);
        }
        return sendMessage;
    }
}
