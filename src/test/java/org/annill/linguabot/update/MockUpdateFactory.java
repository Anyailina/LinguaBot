package org.annill.linguabot.update;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Component
public class MockUpdateFactory {
    private final ObjectMapper objectMapper;
    private final String firstName = "Ann";
    private final String userName = "Anya";
    private final Long userId = 123L;

    public MockUpdateFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Long getUserId() {
        return userId;
    }

    public Update createMockUpdate(String messageText) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(true);
        when(message.getFrom()).thenReturn(user);
        when(message.getChatId()).thenReturn(userId);
        when(user.getId()).thenReturn(userId);
        when(user.getFirstName()).thenReturn(firstName);
        when(user.getUserName()).thenReturn(userName);
        when(message.getText()).thenReturn(messageText);

        return update;
    }

    public String createMockUpdateJson(String messageText) throws JsonProcessingException {
        Update update = createMockUpdate(messageText);
        return objectMapper.writeValueAsString(update);
    }
}
