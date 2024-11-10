package org.annill.linguabot.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.annill.linguabot.model.telegram.TelegramMessage;
import org.annill.linguabot.update.MockUpdateFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@AllArgsConstructor
@Getter
public class MvcTestUtils {
    private final MockUpdateFactory mockUpdateFactory;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public TelegramMessage getSendMessage(String command) throws Exception {
        String updateJson = mockUpdateFactory.createMockUpdateJson(command);
        MvcResult mvcResult = performWebhookTest(updateJson);
        return getSendMessageFromResult(mvcResult);
    }

    private TelegramMessage getSendMessageFromResult(MvcResult result) throws IOException {
        String sendMessageAsString = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        return objectMapper.readValue(sendMessageAsString, TelegramMessage.class);
    }

    public MvcResult performWebhookTest(String updateToJson) throws Exception {
        return mockMvc.perform(post("/webhookTest")
                        .content(updateToJson)
                        .contentType(MediaType.APPLICATION_JSON.getMediaType()))
                .andExpect(status().isOk())
                .andReturn();
    }
}