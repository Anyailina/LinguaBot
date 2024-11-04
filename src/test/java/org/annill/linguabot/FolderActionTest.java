package org.annill.linguabot;

import lombok.AllArgsConstructor;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.update.MockUpdateFactory;
import org.annill.linguabot.utils.MvcTestUtils;
import org.annill.linguabot.utils.WordsMessageUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@AllArgsConstructor
public class FolderActionTest {
    private final MvcTestUtils mvcTestUtils;
    private final WordsMessageUtils wordsMessageUtils;
    private final FolderService folderService;
    private final MockUpdateFactory mockUpdateFactory;

    public void performNameFolderTest(String command) throws Exception {
        SendMessage sendMessage = mvcTestUtils.getSendMessage(command);
        Assertions.assertEquals(wordsMessageUtils.getMessageNameFolder(), sendMessage.getText());
    }

    public void perFormSelectExistFolderTest(String folderName) throws Exception {
        folderService.addFolder(folderName, mockUpdateFactory.getUserId());
        SendMessage sendMessage = mvcTestUtils.getSendMessage(folderName);
        Assertions.assertEquals(wordsMessageUtils.getMessageSendWord(), sendMessage.getText());
    }
}
