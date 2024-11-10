package org.annill.linguabot.commands;

import org.annill.linguabot.configuration.TestConfiguration;
import org.annill.linguabot.model.telegram.TelegramMessage;
import org.annill.linguabot.utils.MvcTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@Import(value = {
        TestConfiguration.class
})
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:message.yaml")
public class DefaultCommandTest {
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
