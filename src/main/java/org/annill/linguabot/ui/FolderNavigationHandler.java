package org.annill.linguabot.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.annill.linguabot.enums.PageEnum;
import org.annill.linguabot.model.cache.SessionCache;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.service.FolderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;

import java.util.Objects;

@Component
@Getter
@AllArgsConstructor
public class FolderNavigationHandler {
    private final Cache cache;
    private final FolderInlineKeyBoard folderInlineKeyBoard;
    private final FolderService folderService;
    @Value("${folder.select}")
    private String folderSelect;

    public BotApiMethod<?> changePage(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        MaybeInaccessibleMessage message = callbackQuery.getMessage();
        SessionCache sessionCache = Objects.requireNonNull(cache.get(chatId, SessionCache.class));

        int currentPage = sessionCache.getCurrentPage();
        int totalPages = folderInlineKeyBoard.getPageSize();
        String callbackData = callbackQuery.getData();

        currentPage = callbackData.equals(PageEnum.NEXT_PAGE.getMessage()) ?
                (currentPage + 1) % totalPages :
                (currentPage - 1 + totalPages) % totalPages;

        sessionCache.setCurrentPage(currentPage);
        cache.put(chatId, sessionCache);

        Page<FolderDto> newPage = folderService.getPageFolderByUserChatId(chatId, currentPage, totalPages);

        return createEditMessage(chatId, message, newPage);
    }

    private EditMessageText createEditMessage(Long chatId, MaybeInaccessibleMessage message, Page<FolderDto> newPage) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId.toString());
        editMessage.setMessageId(message.getMessageId());
        editMessage.setText(folderSelect);
        editMessage.setReplyMarkup(folderInlineKeyBoard.createInlineKeyboard(newPage));
        return editMessage;

    }
}
