package org.annill.linguabot.commands;


import jakarta.persistence.EntityManager;
import org.annill.linguabot.configuration.TestConfiguration;
import org.annill.linguabot.container.PostgresContainer;
import org.annill.linguabot.enums.action.ActionEnum;
import org.annill.linguabot.model.entity.User;
import org.annill.linguabot.model.telegram.TelegramMessage;
import org.annill.linguabot.repository.UserRepository;
import org.annill.linguabot.service.UserService;
import org.annill.linguabot.update.MockUpdateFactory;
import org.annill.linguabot.utils.MvcTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;


@Import(value = {
        TestConfiguration.class
})
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:message.yaml")
public class StartCommandTest extends PostgresContainer {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MockUpdateFactory mockUpdateFactory;
    @Autowired
    private MvcTestUtils mvcTestUtils;
    @Value("${message.start}")
    private String startMessage;
    @Value("${message.user-registered}")
    private String userRegisteredMessage;


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void startCommandIfUserNotExistTest() throws Exception {
        TelegramMessage sendMessage = mvcTestUtils.getSendMessage(ActionEnum.START.getCommandText());

        List<User> results = getUserByChatId(mockUpdateFactory.getUserId());
        Assertions.assertNotNull(results);

        Assertions.assertEquals(startMessage, sendMessage.getText());
    }

    @Test
    void startCommandIfUserExistsTest() throws Exception {
        Update update = mockUpdateFactory.createMockUpdate(ActionEnum.START.getCommandText());
        userService.addUser(update.getMessage().getFrom());

        TelegramMessage sendMessage = mvcTestUtils.getSendMessage(ActionEnum.START.getCommandText());

        List<User> results = getUserByChatId(mockUpdateFactory.getUserId());
        Assertions.assertEquals(results.size(), 1);

        Assertions.assertEquals(sendMessage.getText(), userRegisteredMessage);
    }

    private List<User> getUserByChatId(Long userId) {
        return entityManager.createQuery("select u from User u where u.chatId = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
