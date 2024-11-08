package org.annill.linguabot.ui;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FolderUI {
    private final int itemsPerPage = 4;
    private final String callbackFolder = "folder_";
    private final String nextPage = "nextPage";
    private final String previousPage = "previousPage";

    public void updatePage(String callbackData){
        int currentPage = 0;
        if (callbackData.equals(nextPage)) {
            currentPage = (currentPage + 1) % getTotalPages(); // Переключаемся на следующую страницу
        } else if (callbackData.equals(previousPage)) {
            currentPage = (currentPage - 1 + getTotalPages()) % getTotalPages(); // Переключаемся на предыдущую страницу
        }

        // Создаем новое сообщение с обновленной клавиатурой
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId.toString());
        editMessage.setMessageId(messageId);
        editMessage.setText("Выберите папку:");
        editMessage.setReplyMarkup(createInlineKeyboard(currentPage));
    }
}
