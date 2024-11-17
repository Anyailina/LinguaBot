package org.annill.linguabot.commands;

import org.annill.linguabot.container.AbstractTestContainer;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.telegram.TelegramMessage;
import org.annill.linguabot.repository.FolderRepository;
import org.annill.linguabot.repository.UserRepository;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.update.MockUpdateFactory;
import org.annill.linguabot.utils.MvcTestUtils;
import org.annill.linguabot.utils.WordsMessageUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class AddFolderCommandTest extends AbstractTestContainer {
    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private FolderService folderService;
    @Autowired
    private MockUpdateFactory mockUpdateFactory;
    @Autowired
    private MvcTestUtils mvcTestUtils;
    @Autowired
    private WordsMessageUtils wordsMessageUtils;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        folderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    void setupUserIfNotExist() throws Exception {
        mvcTestUtils.getSendMessage(ActionEnum.START.getCommandText());
    }

    @Test
    void addFolder() throws Exception {
        performAddFolderTest();
        String folderName = wordsMessageUtils.getNameFolder();
        TelegramMessage sendMessageFolderExists = mvcTestUtils.getSendMessage(folderName);

        List<Folder> folders = folderRepository.findAll();
        Assertions.assertEquals(1, folders.size());
        Assertions.assertEquals(folderName, folders.get(0).getName());

        Assertions.assertEquals(wordsMessageUtils.getMessageFolderAdded(), sendMessageFolderExists.getText());
    }

    @Test
    void addExistingFolder() throws Exception {
        performAddFolderTest();

        String folderName = wordsMessageUtils.getNameFolder();
        folderService.addFolder(folderName, mockUpdateFactory.getUserId());

        TelegramMessage sendMessageFolderExists = mvcTestUtils.getSendMessage(wordsMessageUtils.getNameFolder());

        Assertions.assertEquals(wordsMessageUtils.getMessageFolderExits(), sendMessageFolderExists.getText());
    }

    @Test
    void addIncorrectFolder() throws Exception {
        performAddFolderTest();

        TelegramMessage sendMessageFolderExists = mvcTestUtils.getSendMessage("43234");
        Assertions.assertEquals(wordsMessageUtils.getMessageNotCorrectInput(), sendMessageFolderExists.getText());
    }

    private void performAddFolderTest() throws Exception {
        String expectedMessage = wordsMessageUtils.getMessageNameFolder();
        TelegramMessage sendMessageNameFolder = mvcTestUtils.getSendMessage(ActionEnum.ADD_FOLDER.getCommandText());
        Assertions.assertEquals(expectedMessage, sendMessageNameFolder.getText());
    }
}