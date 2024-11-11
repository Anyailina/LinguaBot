package org.annill.linguabot.commands;


import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.transaction.Transactional;
import org.annill.linguabot.FolderActionTest;
import org.annill.linguabot.WordActionTest;
import org.annill.linguabot.WordQueryService;
import org.annill.linguabot.container.AbstractTestContainer;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.model.dto.FolderDto;
import org.annill.linguabot.model.dto.WordSuggestionDto;
import org.annill.linguabot.model.entity.Word;
import org.annill.linguabot.model.telegram.TelegramMessage;
import org.annill.linguabot.repository.FolderRepository;
import org.annill.linguabot.repository.WordRepository;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.service.WordService;
import org.annill.linguabot.update.MockUpdateFactory;
import org.annill.linguabot.utils.MvcTestUtils;
import org.annill.linguabot.utils.WordsMessageUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


@SpringBootTest
@Transactional
public class AddWordCommandTest extends AbstractTestContainer {
    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private MvcTestUtils mvcTestUtils;
    @Autowired
    private WordsMessageUtils wordsMessageUtils;
    @Autowired
    private WordQueryService wordQueryService;
    @Autowired
    private FolderActionTest folderActionTest;
    @Autowired
    private WireMockServer wireMockServer;
    @Autowired
    private WordActionTest wordActionTest;
    @Autowired
    private WordService wordService;
    @Autowired
    private MockUpdateFactory mockUpdateFactory;
    @Autowired
    private FolderService folderService;


    @BeforeEach
    void setupUserIfNotExist() throws Exception {
        wireMockServer.start();

        List<WordSuggestionDto> wordSuggestionDtoList = new ArrayList<>();
        String json = mvcTestUtils.getObjectMapper().writeValueAsString(wordSuggestionDtoList);

        stubFor(post(urlEqualTo("/words"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(json))
        );
        mvcTestUtils.getSendMessage(ActionEnum.START.getCommandText());
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
        wordRepository.deleteAll();
        folderRepository.deleteAll();
    }

    @Test
    void addWordWithoutFolder() throws Exception {
        TelegramMessage sendMessage = mvcTestUtils.getSendMessage(ActionEnum.ADD_WORD.getCommandText());
        Assertions.assertEquals(wordsMessageUtils.getMessageNotExistsFolder(), sendMessage.getText());
    }


    @Test
    void addWordFolderNotExist() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        folderActionTest.perFormFolderList(folderName);
        TelegramMessage telegramMessage = mvcTestUtils.getSendMessage(wordsMessageUtils.getNotExistsFolder());
        Assertions.assertEquals(wordsMessageUtils.getMessageFolderNotExits(), telegramMessage.getText());
    }

    @Test
    void addWord() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        folderActionTest.perFormFolderList(folderName);
        wordActionTest.equalsAssertion(folderName, wordsMessageUtils.getMessageSendWord());
        wordActionTest.equalsAssertion(wordsMessageUtils.getWord(), wordsMessageUtils.getMessageSendTranslation());

        wordActionTest.equalsAssertion(wordsMessageUtils.getTranslation(), wordsMessageUtils.getMessageWordAdded());

        Word word = wordQueryService.getWordByNameAndTranslation(wordsMessageUtils.getWord(), wordsMessageUtils.getTranslation());
        Assertions.assertNotNull(word);
    }

    @Test
    void addExistsWord() throws Exception {

        String folderName = wordsMessageUtils.getNameFolder();
        folderActionTest.perFormFolderList(folderName);
        wordActionTest.equalsAssertion(folderName, wordsMessageUtils.getMessageSendWord());

        wordActionTest.equalsAssertion(wordsMessageUtils.getWord(), wordsMessageUtils.getMessageSendTranslation());
        FolderDto folderDto = folderService.getFolderByName(folderName, mockUpdateFactory.getUserId());
        wordService.addWord(folderDto.getId(), wordsMessageUtils.getWord(), wordsMessageUtils.getTranslation(), mockUpdateFactory.getUserId());

        wordActionTest.equalsAssertion(wordsMessageUtils.getTranslation(), wordsMessageUtils.getMessageTranslationExists());
    }

    @Test
    void addIncorrectWord() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        folderActionTest.perFormFolderList(folderName);
        wordActionTest.equalsAssertion(folderName, wordsMessageUtils.getMessageSendWord());
        wordActionTest.equalsAssertion("8430", wordsMessageUtils.getMessageNotCorrectInput());
    }

    @Test
    void addIncorrectTranslation() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        folderActionTest.perFormFolderList(folderName);
        wordActionTest.equalsAssertion(folderName, wordsMessageUtils.getMessageSendWord());
        wordActionTest.equalsAssertion(wordsMessageUtils.getWord(), wordsMessageUtils.getMessageSendTranslation());
        wordActionTest.equalsAssertion("8430", wordsMessageUtils.getMessageNotCorrectInput());
    }
}
