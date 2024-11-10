package org.annill.linguabot;

import lombok.AllArgsConstructor;
import org.annill.linguabot.model.telegram.TelegramMessage;
import org.annill.linguabot.utils.MvcTestUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WordActionTest {
    private MvcTestUtils mvcTestUtils;
    public void equalsAssertion(String request , String expectedAnswer) throws Exception {
        TelegramMessage telegramMessage = mvcTestUtils.getSendMessage(request);
        Assertions.assertEquals(expectedAnswer, telegramMessage.getText());
    }
}
