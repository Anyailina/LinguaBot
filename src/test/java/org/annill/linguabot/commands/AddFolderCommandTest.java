package org.annill.linguabot.commands;

import jakarta.persistence.EntityManager;
import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.model.entity.Folder;
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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:message.yaml")
public class AddFolderCommandTest {
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
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


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @AfterEach
    void tearDown() {
        folderRepository.deleteAll();
    }

    @BeforeEach
    void setupUserIfNotExist() throws Exception {
        mvcTestUtils.getSendMessage(ActionEnum.START.getCommandText());
    }

    @Test
    void addNewFolderTest() throws Exception {
        performAddFolderTest();
        String folderName = wordsMessageUtils.getNameFolder();
        SendMessage sendMessageFolderExists = mvcTestUtils.getSendMessage(folderName);
        List<Folder> folders = getFoldersByName(folderName);
        Assertions.assertEquals(wordsMessageUtils.getMessageFolderAdded(), sendMessageFolderExists.getText());
        Assertions.assertEquals(1, folders.size());
    }

    @Test
    void addExistingFolderTest() throws Exception {
        performAddFolderTest();

        String folderName = wordsMessageUtils.getNameFolder();
        folderService.addFolder(folderName, mockUpdateFactory.getUserId());

        SendMessage sendMessageFolderExists = mvcTestUtils.getSendMessage(wordsMessageUtils.getNameFolder());
        List<Folder> folders = getFoldersByName(folderName);

        Assertions.assertEquals(wordsMessageUtils.getMessageFolderExits(), sendMessageFolderExists.getText());
        Assertions.assertEquals(1, folders.size());
    }

    private void performAddFolderTest() throws Exception {
        String expectedMessage = wordsMessageUtils.getMessageNameFolder();
        SendMessage sendMessageNameFolder = mvcTestUtils.getSendMessage(ActionEnum.ADD_FOLDER.getCommandText());
        Assertions.assertEquals(expectedMessage, sendMessageNameFolder.getText());

    }


    private List<Folder> getFoldersByName(String name) {
        return entityManager.createQuery("select f from Folder f where f.name = :name", Folder.class)
                .setParameter("name", name)
                .getResultList();
    }
}
