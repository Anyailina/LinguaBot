package org.annill.linguabot.commands;


import org.annill.linguabot.container.AbstractTestContainer;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;


@SpringBootTest
@TestPropertySource(locations = "classpath:message.yaml")
public class StartCommandTest extends AbstractTestContainer {
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
        cache.clear();
    }

    @Test
    void startCommandIfUserNotExistTest() throws Exception {
        TelegramMessage sendMessage = mvcTestUtils.getSendMessage(ActionEnum.START.getCommandText());

        Optional<User> user = userRepository.findByChatId(mockUpdateFactory.getUserId());
        Assertions.assertTrue(user.isPresent());

        Assertions.assertEquals(startMessage, sendMessage.getText());
    }

    @Test
    void startCommandIfUserExistsTest() throws Exception {
        Update update = mockUpdateFactory.createMockUpdate(ActionEnum.START.getCommandText());
        userService.addUser(update.getMessage().getFrom());

        TelegramMessage sendMessage = mvcTestUtils.getSendMessage(ActionEnum.START.getCommandText());

        Optional<User> user = userRepository.findByChatId(mockUpdateFactory.getUserId());
        Assertions.assertTrue(user.isPresent());

        Assertions.assertEquals(sendMessage.getText(), userRegisteredMessage);
    }
}