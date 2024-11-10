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
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

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

    public SendMessage changePage(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
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

        return createSendMessage(chatId, newPage);
    }

    private SendMessage createSendMessage(Long chatId, Page<FolderDto> newPage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(folderSelect);
        sendMessage.setReplyMarkup(folderInlineKeyBoard.createInlineKeyboard(newPage));
        return sendMessage;
    }
}
