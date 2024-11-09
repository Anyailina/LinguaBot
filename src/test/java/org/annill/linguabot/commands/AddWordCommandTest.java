package org.annill.linguabot.commands;


import com.github.tomakehurst.wiremock.WireMockServer;
import org.annill.linguabot.FolderActionTest;
import org.annill.linguabot.WordQueryService;
import org.annill.linguabot.configuration.WireMockConfiguration;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.model.entity.Word;
import org.annill.linguabot.repository.FolderRepository;
import org.annill.linguabot.repository.WordRepository;
import org.annill.linguabot.service.WordService;
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
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Import(value = {
        WireMockConfiguration.class
})
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class AddWordCommandTest {
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private WordService wordService;
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private MockUpdateFactory mockUpdateFactory;
    @Autowired
    private MvcTestUtils mvcTestUtils;
    @Autowired
    private WordsMessageUtils wordsMessageUtils;
    @Autowired
    private WordQueryService wordQueryService;
    @Autowired
    private FolderActionTest folderActionTest;
    @Autowired
    private Cache cache;
    @Autowired
    private WireMockServer wireMockServer;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void setupUserIfNotExist() throws Exception {
        wireMockServer.start();

        List<WordSuggestionDto> wordSuggestionDtoList = new ArrayList<>();
        String json = mvcTestUtils.getObjectMapper().writeValueAsString(wordSuggestionDtoList);

        stubFor(post(urlEqualTo("/words"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(json))
        );
        mvcTestUtils.getSendMessage(ActionEnum.START.getCommandText());
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
        wordRepository.deleteAll();
        folderRepository.deleteAll();
        cache.clear();
    }


    @Test
    void addWordFolderNotExist() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        folderActionTest.performNameFolderTest(ActionEnum.ADD_WORD.getCommandText());
        SendMessage sendMessage = mvcTestUtils.getSendMessage(folderName);
        Assertions.assertEquals(wordsMessageUtils.getMessageFolderNotExits(), sendMessage.getText());
    }

    @Test
    void addWord() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        folderActionTest.performNameFolderTest(ActionEnum.ADD_WORD.getCommandText());
        folderActionTest.perFormSelectExistFolderTest(folderName);

        SendMessage sendMessage = mvcTestUtils.getSendMessage(wordsMessageUtils.getWord());
        Assertions.assertEquals(wordsMessageUtils.getMessageSendTranslation(), sendMessage.getText());

        perFormTranslationTest(wordsMessageUtils.getTranslation());

        Word word = wordQueryService.getWordByNameAndTranslation(wordsMessageUtils.getWord(), wordsMessageUtils.getTranslation());
        Assertions.assertNotNull(word);
    }

    @Test
    void addWordWithExistWord() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        folderActionTest.performNameFolderTest(ActionEnum.ADD_WORD.getCommandText());
        folderActionTest.perFormSelectExistFolderTest(folderName);

        SendMessage sendMessage = mvcTestUtils.getSendMessage(wordsMessageUtils.getWord());
        Assertions.assertEquals(wordsMessageUtils.getMessageSendTranslation(), sendMessage.getText());
        // wordService.addWord(folderName, wordsMessageUtils.getWord(), wordsMessageUtils.getTranslation(), mockUpdateFactory.getUserId());

        SendMessage sendMessageTranslation = mvcTestUtils.getSendMessage(wordsMessageUtils.getTranslation());
        Assertions.assertEquals(wordsMessageUtils.getMessageTranslationExists(), sendMessageTranslation.getText());
    }

    private void perFormTranslationTest(String translation) throws Exception {
        SendMessage sendMessage = mvcTestUtils.getSendMessage(translation);
        Assertions.assertEquals(wordsMessageUtils.getMessageWordAdded(), sendMessage.getText());
    }

}
