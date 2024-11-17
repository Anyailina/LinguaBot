package org.annill.linguabot.handler.action.abs;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.PageEnum;
import org.annill.linguabot.handler.action.ActionHandler;
import org.annill.linguabot.model.cache.SessionCache;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.ui.FolderInlineKeyBoard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;

import java.util.Objects;

@AllArgsConstructor
public abstract class FolderNavigationActionHandler implements ActionHandler {
    private final Cache cache;
    private final FolderInlineKeyBoard folderInlineKeyBoard;
    private final FolderService folderService;
    @Value("${folder.select}")
    private String folderSelect;

    protected EditMessageText changePage(PageEnum pageEnum, CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        SessionCache sessionCache = Objects.requireNonNull(cache.get(chatId, SessionCache.class));

        int currentPage = sessionCache.getCurrentPage();
        int totalPages = folderInlineKeyBoard.getPageSize();

        switch (pageEnum) {
            case NEXT_PAGE:
                currentPage = (currentPage + 1) % totalPages;
                break;
            case PREVIOUS_PAGE:
                currentPage = (currentPage - 1 + totalPages) % totalPages;
                break;
            default:
                break;
        }

        sessionCache.setCurrentPage(currentPage);
        cache.put(chatId, sessionCache);

        Page<FolderDto> newPage = folderService.getPageFolderByUserChatId(chatId, currentPage, totalPages);

        return createSendMessage(chatId, newPage, callbackQuery.getMessage());
    }

    private EditMessageText createSendMessage(Long chatId, Page<FolderDto> newPage, MaybeInaccessibleMessage message) {
        EditMessageText sendMessage = new EditMessageText();
        sendMessage.setMessageId(message.getMessageId());
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(folderSelect);
        sendMessage.setReplyMarkup(folderInlineKeyBoard.createInlineKeyboard(newPage));
        return sendMessage;
    }
}
