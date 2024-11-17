package org.annill.linguabot;

import lombok.AllArgsConstructor;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.entity.User;
import org.annill.linguabot.model.telegram.TelegramMessage;
import org.annill.linguabot.repository.FolderRepository;
import org.annill.linguabot.repository.UserRepository;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.ui.FolderInlineKeyBoard;
import org.annill.linguabot.update.MockUpdateFactory;
import org.annill.linguabot.utils.MvcTestUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@AllArgsConstructor
public class FolderActionTest {
    private final MvcTestUtils mvcTestUtils;
    private final FolderService folderService;
    private final MockUpdateFactory mockUpdateFactory;
    private final FolderInlineKeyBoard folderInlineKeyBoard;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;

    public void perFormFolderList(String folderName) throws Exception {
        addFolder(folderName);
        Page<FolderDto> folderDto = folderService.getPageFolderByUserChatId(mockUpdateFactory.getUserId(), 0, folderInlineKeyBoard.getPageSize());
        InlineKeyboardMarkup inlineKeyboardMarkup = folderInlineKeyBoard.createInlineKeyboard(folderDto);
        TelegramMessage sendMessage = mvcTestUtils.getSendMessage(ActionEnum.ADD_WORD.getCommandText());

        String actualText = inlineKeyboardMarkup.getKeyboard().get(0).get(0).getText();
        String expectedText = sendMessage.getReplyMarkup().getInlineKeyboard().get(0).get(0).getText();

        Assertions.assertEquals(actualText, expectedText);
    }

    public void addFolder(String folderName) {
        User user = userRepository.findByChatId(mockUpdateFactory.getUserId()).get();
        Folder folder = new Folder(folderName, user);

        folderRepository.save(folder);
    }
}