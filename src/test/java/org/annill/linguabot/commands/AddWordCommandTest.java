package org.annill.linguabot.commands;


import jakarta.persistence.EntityManager;
import org.annill.linguabot.enums.ActionEnum;
import org.annill.linguabot.model.entity.Word;
import org.annill.linguabot.model.entity.WordSuggestion;
import org.annill.linguabot.repository.FolderRepository;
import org.annill.linguabot.repository.WordRepository;
import org.annill.linguabot.repository.WordSuggestionRepository;
import org.annill.linguabot.service.FolderService;
import org.annill.linguabot.service.WordService;
import org.annill.linguabot.service.WordSuggestionService;
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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class AddWordCommandTest {
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
    private WordService wordService;
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private WordSuggestionService wordSuggestionService;
    @Autowired
    private WordSuggestionRepository wordSuggestionRepository;
    @Autowired
    private MockUpdateFactory mockUpdateFactory;
    @Autowired
    private MvcTestUtils mvcTestUtils;
    @Autowired
    private WordsMessageUtils wordsMessageUtils;
    @Autowired
    private Cache cache;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @AfterEach
    void tearDown() {
        wordRepository.deleteAll();
        folderRepository.deleteAll();
        wordSuggestionRepository.deleteAll();
        cache.clear();
    }

    @BeforeEach
    void setupUserIfNotExist() throws Exception {
        mvcTestUtils.getSendMessage(ActionEnum.START.getCommandText());
    }

    @Test
    void addWordFolderNotExist() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        performNameFolderTest(ActionEnum.ADD_WORD.getCommandText());
        SendMessage sendMessage = mvcTestUtils.getSendMessage(folderName);
        Assertions.assertEquals(wordsMessageUtils.getMessageFolderNotExits(), sendMessage.getText());
    }

    @Test
    void addWordWithoutSuggestion() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        performNameFolderTest(ActionEnum.ADD_WORD.getCommandText());
        perFormSelectExistFolderTest(folderName);

        SendMessage sendMessage = mvcTestUtils.getSendMessage(wordsMessageUtils.getWord());
        Assertions.assertEquals(wordsMessageUtils.getMessageSendTranslation(), sendMessage.getText());

        perFormTranslationTest(wordsMessageUtils.getTranslation());

        Word word = getWordByNameAndTranslation(wordsMessageUtils.getWord(), wordsMessageUtils.getTranslation());
        Assertions.assertNotNull(word);
    }

    @Test
    void addWordWithoutSuggestionWithExistWord() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        performNameFolderTest(ActionEnum.ADD_WORD.getCommandText());
        perFormSelectExistFolderTest(folderName);

        SendMessage sendMessage = mvcTestUtils.getSendMessage(wordsMessageUtils.getWord());
        Assertions.assertEquals(wordsMessageUtils.getMessageSendTranslation(), sendMessage.getText());
        wordService.addWord(folderName, wordsMessageUtils.getWord(), wordsMessageUtils.getTranslation(), mockUpdateFactory.getUserId());

        SendMessage sendMessageTranslation = mvcTestUtils.getSendMessage(wordsMessageUtils.getTranslation());
        Assertions.assertEquals(wordsMessageUtils.getMessageTranslationExists(), sendMessageTranslation.getText());
    }

    @Test
    void addWordExistsWithSuggestion() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        performNameFolderTest(ActionEnum.ADD_WORD.getCommandText());
        perFormSelectExistFolderTest(folderName);

        SendMessage sendMessageWord = mvcTestUtils.getSendMessage(wordsMessageUtils.getWord());
        Assertions.assertEquals(wordsMessageUtils.getMessageSendTranslation(), sendMessageWord.getText());
        wordService.addWord(folderName, wordsMessageUtils.getWord(), wordsMessageUtils.getTranslation(), mockUpdateFactory.getUserId());

        SendMessage sendMessageTranslation = mvcTestUtils.getSendMessage(wordsMessageUtils.getTranslation());

        Assertions.assertEquals(wordsMessageUtils.getMessageTranslationExists(), sendMessageTranslation.getText());
    }

    @Test
    void addWordWithSuggestion() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        performNameFolderTest(ActionEnum.ADD_WORD.getCommandText());
        perFormSelectExistFolderTest(folderName);

        SendMessage sendMessageWord = mvcTestUtils.getSendMessage(wordsMessageUtils.getWord());
        Assertions.assertEquals(wordsMessageUtils.getMessageSendTranslation(), sendMessageWord.getText());

        SendMessage sendMessageTranslation = mvcTestUtils.getSendMessage(wordsMessageUtils.getTranslation());

        Assertions.assertEquals(wordsMessageUtils.getMessageWordAdded(), sendMessageTranslation.getText());
    }

    @Test
    void addWordWithSuggestionWithCorrectNumber() throws Exception {
        performAddWordWithSuggestion();
        SendMessage sendMessage = mvcTestUtils.getSendMessage("1");

        Word word = getWordByNameAndTranslation(wordsMessageUtils.getWord(), wordsMessageUtils.getTranslationSuggestion());
        Assertions.assertNotNull(word);
        Assertions.assertEquals(wordsMessageUtils.getMessageWordAdded(), sendMessage.getText());
    }

    @Test
    void addWordWithSuggestionWithIncorrectNumber() throws Exception {
        performAddWordWithSuggestion();
        SendMessage sendMessage = mvcTestUtils.getSendMessage("-10");

        Word word = getWordByNameAndTranslation(wordsMessageUtils.getWord(), wordsMessageUtils.getTranslationSuggestion());
        Assertions.assertNull(word);
        Assertions.assertEquals(wordsMessageUtils.getMessageIncorrectNumber(), sendMessage.getText());
    }

    @Test
    void addWordWithSuggestionWithAnotherTranslation() throws Exception {
        performAddWordWithSuggestion();
        SendMessage sendMessage = mvcTestUtils.getSendMessage(wordsMessageUtils.getAnotherTranslation());

        Word word = getWordByNameAndTranslation(wordsMessageUtils.getWord(), wordsMessageUtils.getAnotherTranslation());
        Assertions.assertNotNull(word);
        Assertions.assertEquals(wordsMessageUtils.getMessageWordAdded(), sendMessage.getText());
    }

    private void performNameFolderTest(String command) throws Exception {
        SendMessage sendMessage = mvcTestUtils.getSendMessage(command);
        Assertions.assertEquals(wordsMessageUtils.getMessageNameFolder(), sendMessage.getText());
    }

    private void perFormSelectExistFolderTest(String folderName) throws Exception {
        folderService.addFolder(folderName, mockUpdateFactory.getUserId());
        SendMessage sendMessage = mvcTestUtils.getSendMessage(folderName);
        Assertions.assertEquals(wordsMessageUtils.getMessageSendWord(), sendMessage.getText());
    }

    private void performAddWordWithSuggestion() throws Exception {
        String folderName = wordsMessageUtils.getNameFolder();
        performNameFolderTest(ActionEnum.ADD_WORD.getCommandText());
        perFormSelectExistFolderTest(folderName);

        addWordSuggestions();
        List<WordSuggestion> wordsSuggestion = getWordSuggestions(wordsMessageUtils.getWordSuggestion());
        List<Word> words = getsWordByName(wordsMessageUtils.getWord());

        List<WordSuggestion> savedWords = getSavedWords(words);
        wordsSuggestion.removeAll(savedWords);

        String answerPhrase = createSuggestionMessage(wordsSuggestion);
        verifySendMessage(answerPhrase);
    }

    private void addWordSuggestions() {
        String wordSuggestion = wordsMessageUtils.getWordSuggestion();
        wordSuggestionService.addWord(wordSuggestion, wordsMessageUtils.getTranslationSuggestion());
        wordSuggestionService.addWord(wordSuggestion, wordsMessageUtils.getTranslationSuggestion());
    }

    private List<WordSuggestion> getWordSuggestions(String wordSuggestion) {
        return new ArrayList<>(getSuggestionWordsByPhrase(wordSuggestion));
    }

    private List<WordSuggestion> getSavedWords(List<Word> words) {
        return words.stream()
                .map(element -> new WordSuggestion(element.getId(), element.getName(), element.getTranslation()))
                .collect(Collectors.toList());
    }

    private void verifySendMessage(String expectedMessage) throws Exception {
        SendMessage sendMessage = mvcTestUtils.getSendMessage(wordsMessageUtils.getWord());
        Assertions.assertEquals(expectedMessage, sendMessage.getText());
    }

    private void perFormTranslationTest(String translation) throws Exception {
        SendMessage sendMessage = mvcTestUtils.getSendMessage(translation);
        Assertions.assertEquals(wordsMessageUtils.getMessageWordAdded(), sendMessage.getText());
    }

    private Word getWordByNameAndTranslation(String word, String translation) {
        List<Word> words = entityManager.createQuery(
                        "select w from Word w where w.name = :name and w.translation = :translation", Word.class)
                .setParameter("name", word)
                .setParameter("translation", translation)
                .getResultList();
        return words.isEmpty() ? null : words.get(0);
    }


    private List<Word> getsWordByName(String word) {
        return entityManager.createQuery("select w from Word w where w.name = :name", Word.class)
                .setParameter("name", word)
                .getResultList();
    }

    private List<WordSuggestion> getSuggestionWordsByPhrase(String phrase) {
        return entityManager.createQuery("select w from WordSuggestion w where w.phrase = :phrase", WordSuggestion.class)
                .setParameter("phrase", phrase)
                .getResultList();
    }

    private String createSuggestionMessage(List<WordSuggestion> wordSuggestionDtoList) {
        StringBuilder answer = new StringBuilder(wordsMessageUtils.getMessageWordSuggestionExists()).append("\n");
        for (int i = 0; i < wordSuggestionDtoList.size(); i++) {
            answer.append(i + 1).append(". ").append(wordSuggestionDtoList.get(i).getTranslation()).append("\n");
        }
        return answer.toString();
    }
}
