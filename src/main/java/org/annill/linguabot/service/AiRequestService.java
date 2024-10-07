package org.annill.linguabot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.annill.linguabot.feignClient.ExternalAiFeignClient;
import org.annill.linguabot.feignClient.ExternalTokenFeignClient;
import org.annill.linguabot.parsing.ChatCompletion;
import org.annill.linguabot.parsing.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiRequestService {
    private final ExternalTokenFeignClient externalTokenApi;
    private final ExternalAiFeignClient externalAiApi;
    @Value("${ai.payload}")
    private String payLoad;

    public AiRequestService(ExternalTokenFeignClient externalTokenApi, ExternalAiFeignClient externalAiApi) {
        this.externalTokenApi = externalTokenApi;
        this.externalAiApi = externalAiApi;
    }


    public String getAnswer(String request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String accessTokenJson = externalTokenApi.getAccessToken(payLoad);
        Token token = objectMapper.readValue(accessTokenJson, Token.class);

        String jsonRequest = String.format("""
                {
                  "model": "GigaChat",
                  "messages": [
                    {
                      "role": "user",
                      "content": "%s"
                    }
                  ],
                  "stream": false,
                  "update_interval": 0
                }
                """, request);

        String answerFromAi = externalAiApi.getAnswer(jsonRequest, token.getAccessToken());
        ChatCompletion chatCompletion = objectMapper.readValue(answerFromAi, ChatCompletion.class);
        ChatCompletion.Choice choice = chatCompletion.getChoices()
                .stream()
                .findFirst()
                .orElseThrow();

        return choice.getMessage().getContent();
    }
}
