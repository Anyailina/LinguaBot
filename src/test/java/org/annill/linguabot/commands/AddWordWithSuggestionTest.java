package org.annill.linguabot.commands;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.annill.linguabot.FolderActionTest;
import org.annill.linguabot.WordActionTest;
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
import org.springframework.cache.Cache;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonGenerator;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
public class AddWordWithSuggestionTest extends AbstractTestContainer {
    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private WireMockServer wireMockServer;
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
    private FolderActionTest folderActionTest;
    @Autowired
    private Cache cache;
    @Autowired
    private FolderService folderService;
    @Autowired
    private WordActionTest wordActionTest;


    @BeforeEach
    void setupUserIfNotExist() throws Exception {
        mvcTestUtils.getSendMessage(ActionEnum.START.getCommandText());
        wireMockServer.start();
        getWordsSuggestion();

        stubFor(post(urlEqualTo("/words"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getWordsSuggestion()))
        );
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
        wordRepository.deleteAll();
        folderRepository.deleteAll();
        cache.clear();
    }

    @Test
    void addWordExists() throws Exception {
        performAddWord();
        String folderName = wordsMessageUtils.getNameFolder();
        FolderDto folderDto = folderService.getFolderByName(folderName, mockUpdateFactory.getUserId());

        wordService.addWord(folderDto.getId(), wordsMessageUtils.getWord(), wordsMessageUtils.getTranslation(), mockUpdateFactory.getUserId());

        TelegramMessage telegramMessage = mvcTestUtils.getSendMessage(wordsMessageUtils.getTranslation());

        Assertions.assertEquals(wordsMessageUtils.getMessageTranslationExists(), telegramMessage.getText());
    }

    @Test
    void addWordWithCorrectNumber() throws Exception {
        performAddWord();
        TelegramMessage sendMessage = mvcTestUtils.getSendMessage("1");

        Optional<Word> word = wordRepository.findFirstByNameAndTranslation(wordsMessageUtils.getWord(), wordsMessageUtils.getTranslationSuggestion());
        Assertions.assertTrue(word.isPresent());

        Assertions.assertEquals(wordsMessageUtils.getMessageWordAdded(), sendMessage.getText());
    }

    @Test
    void addWordWithIncorrectNumber() throws Exception {
        performAddWord();
        TelegramMessage sendMessage = mvcTestUtils.getSendMessage("-10");

        Optional<Word> word = wordRepository.findFirstByNameAndTranslation(wordsMessageUtils.getWord(), wordsMessageUtils.getTranslationSuggestion());
        Assertions.assertTrue(word.isEmpty());

        Assertions.assertEquals(wordsMessageUtils.getMessageIncorrectInput(), sendMessage.getText());
    }

    @Test
    void addWordWithNotSuggestionTranslation() throws Exception {
        performAddWord();
        TelegramMessage sendMessage = mvcTestUtils.getSendMessage(wordsMessageUtils.getAnotherTranslation());

        Optional<Word> word = wordRepository.findFirstByNameAndTranslation(wordsMessageUtils.getWord(), wordsMessageUtils.getAnotherTranslation());
        Assertions.assertTrue(word.isPresent());

        Assertions.assertEquals(wordsMessageUtils.getMessageWordAdded(), sendMessage.getText());
    }

    private void performAddWord() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        folderActionTest.perFormFolderList(folderName);
        wordActionTest.equalsAssertion(folderName, wordsMessageUtils.getMessageSendWord());

        List<WordSuggestionDto> wordsSuggestion = getSuggestionWordsByPhrase();
        List<Word> words = wordRepository.findAllByName(wordsMessageUtils.getWord());

        List<WordSuggestionDto> savedWords = getSavedWords(words);
        wordsSuggestion.removeAll(savedWords);

        String answerPhrase = createSuggestionMessage(wordsSuggestion);
        verifySendMessage(answerPhrase);
    }


    private List<WordSuggestionDto> getSavedWords(List<Word> words) {
        return words.stream()
                .map(element -> new WordSuggestionDto(element.getName(), element.getTranslation()))
                .collect(Collectors.toList());
    }

    private void verifySendMessage(String expectedMessage) throws Exception {
        TelegramMessage sendMessage = mvcTestUtils.getSendMessage(wordsMessageUtils.getWord());
        Assertions.assertEquals(expectedMessage, sendMessage.getText());
    }


    private List<WordSuggestionDto> getSuggestionWordsByPhrase() {
        WordSuggestionDto wordSuggestionDto = new WordSuggestionDto(
                wordsMessageUtils.getWordSuggestion(),
                wordsMessageUtils.getTranslationSuggestion()
        );

        return new ArrayList<>(List.of(wordSuggestionDto));
    }

    private String createSuggestionMessage(List<WordSuggestionDto> wordSuggestionDtoList) {
        StringBuilder answer = new StringBuilder(wordsMessageUtils.getMessageWordSuggestionExists()).append("\n");
        for (int i = 0; i < wordSuggestionDtoList.size(); i++) {
            answer.append(i + 1).append(". ").append(wordSuggestionDtoList.get(i).getTranslation()).append("\n");
        }
        return answer.toString();
    }


    public String getWordsSuggestion() throws JsonProcessingException {
        WordSuggestionDto wordSuggestionDto = new WordSuggestionDto(
                wordsMessageUtils.getWordSuggestion(),
                wordsMessageUtils.getTranslationSuggestion()
        );
        List<WordSuggestionDto> wordSuggestionDtoList = List.of(wordSuggestionDto);

        return mvcTestUtils.getObjectMapper()
                .configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true)
                .writeValueAsString(wordSuggestionDtoList);
    }
}
