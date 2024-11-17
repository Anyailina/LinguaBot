package org.annill.linguabot.handler.action.abs.impl;

import org.annill.linguabot.enums.PageEnum;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.handler.action.abs.FolderNavigationActionHandler;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.ui.FolderInlineKeyBoard;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PreviousPageActionHandler extends FolderNavigationActionHandler {

    public PreviousPageActionHandler(Cache cache, FolderInlineKeyBoard folderInlineKeyBoard, FolderService folderService, String folderSelect) {
        super(cache, folderInlineKeyBoard, folderService, folderSelect);
    }

    @Override
    public ActionEnum getType() {
        return ActionEnum.PREVIOUS_PAGE;
    }

    @Override
    public EditMessageText process(String text, Update update) {
        return changePage(PageEnum.PREVIOUS_PAGE, update.getCallbackQuery());
    }
}
