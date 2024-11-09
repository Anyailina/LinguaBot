package org.annill.linguabot.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.annill.linguabot.enums.PageEnum;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.service.FolderService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@RequiredArgsConstructor
public class FolderInlineKeyBoard {
    private static final int PAGE_SIZE = 4;
    private static final String CALLBACK_PREFIX = "folder_";
    private static final String NEXT_PAGE_TEXT = "Вперед ➡️";
    private static final String PREVIOUS_PAGE_TEXT = "⬅️ Назад";
    private final FolderService folderService;

    public int getPageSize() {
        return PAGE_SIZE;
    }

    public InlineKeyboardMarkup createInlineKeyboard(Page<FolderDto> folderPage) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<FolderDto> folders = folderPage.getContent();
        for (int i = 0; i < folders.size(); i += 2) {
            rowsInline.add(getInlineKeyboardButtons(folders, i));
        }

        List<InlineKeyboardButton> navigationButtons = new ArrayList<>();
        if (folderPage.hasPrevious()) {
            navigationButtons.add(createNavigationButton(PREVIOUS_PAGE_TEXT, PageEnum.PREVIOUS_PAGE.getMessage()));
        }
        if (folderPage.hasNext()) {
            navigationButtons.add(createNavigationButton(NEXT_PAGE_TEXT, PageEnum.NEXT_PAGE.getMessage()));
        }
        if (!navigationButtons.isEmpty()) {
            rowsInline.add(navigationButtons);
        }

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private List<InlineKeyboardButton> getInlineKeyboardButtons(List<FolderDto> folders, int index) {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(createFolderButton(folders.get(index).getName()));
        if (index + 1 < folders.size()) {
            rowInline.add(createFolderButton(folders.get(index + 1).getName()));
        }
        return rowInline;
    }

    private InlineKeyboardButton createFolderButton(String folderName) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(folderName);
        button.setCallbackData(CALLBACK_PREFIX + folderName);
        return button;
    }

    private InlineKeyboardButton createNavigationButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }
}
