package org.annill.linguabot.utils;


import org.annill.linguabot.update.MockUpdateFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class MvcTestUtils {
    private final MockUpdateFactory mockUpdateFactory;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public MvcTestUtils(MockUpdateFactory mockUpdateFactory, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockUpdateFactory = mockUpdateFactory;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public SendMessage getSendMessage(String command) throws Exception {
        String updateJson = mockUpdateFactory.createMockUpdateJson(command);
        MvcResult mvcResult = performWebhookTest(updateJson);
        return getSendMessageFromResult(mvcResult);
    }

    private SendMessage getSendMessageFromResult(MvcResult result) throws IOException {
        String sendMessageAsString = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        return objectMapper.readValue(sendMessageAsString, SendMessage.class);
    }

    private MvcResult performWebhookTest(String updateToJson) throws Exception {
        return mockMvc.perform(post("/webhookTest")
                        .content(updateToJson)
                        .contentType(MediaType.APPLICATION_JSON.getMediaType()))
                .andExpect(status().isOk())
                .andReturn();
    }
}