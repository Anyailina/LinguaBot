package org.annill.linguabot.commands;

import jakarta.persistence.EntityManager;
import org.annill.linguabot.container.AbstractTestContainer;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.model.entity.Folder;
import org.annill.linguabot.model.telegram.TelegramMessage;
import org.annill.linguabot.repository.FolderRepository;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.update.MockUpdateFactory;
import org.annill.linguabot.utils.MvcTestUtils;
import org.annill.linguabot.utils.WordsMessageUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;


@SpringBootTest
public class AddFolderCommandTest extends AbstractTestContainer {
    @Autowired
    private EntityManager entityManager;
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

    @AfterEach
    void tearDown() {
        folderRepository.deleteAll();
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
        List<Folder> folders = getFoldersByName(folderName);
        Assertions.assertEquals(wordsMessageUtils.getMessageFolderAdded(), sendMessageFolderExists.getText());
        Assertions.assertEquals(1, folders.size());
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


    private List<Folder> getFoldersByName(String name) {
        return entityManager.createQuery("select f from Folder f where f.name = :name", Folder.class)
                .setParameter("name", name)
                .getResultList();
    }
}
