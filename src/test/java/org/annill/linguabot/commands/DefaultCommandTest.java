package org.annill.linguabot.commands;

import org.annill.linguabot.container.AbstractTestContainer;
import org.annill.linguabot.model.telegram.TelegramMessage;
import org.annill.linguabot.utils.MvcTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DefaultCommandTest extends AbstractTestContainer {
    @Autowired
    private MvcTestUtils mvcTestUtils;
    @Value("${message.default}")
    private String defaultMessage;

    @Test
    void defaultCommandTest() throws Exception {
        TelegramMessage sendMessage = mvcTestUtils.getSendMessage("jfsdl");
        Assertions.assertEquals(defaultMessage, sendMessage.getText());
    }
}