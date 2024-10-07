package org.annill.linguabot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.annill.linguabot.Token;
import org.annill.linguabot.configuration.ai.AiConfiguration;
import org.annill.linguabot.configuration.feignClient.ExternalAiApi;
import org.annill.linguabot.configuration.feignClient.ExternalTokenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiRequestController {
    private final AiConfiguration aiConfiguration;
    private final ExternalTokenAiApi externalTokenApi;
    private final ExternalAiApi externalAiApi;
    @Value("${ai.payload}")
    private String payLoad;

    public AiRequestController(AiConfiguration aiConfiguration, ExternalTokenAiApi externalTokenApi, ExternalAiApi externalAiApi) {
        this.aiConfiguration = aiConfiguration;
        this.externalTokenApi = externalTokenApi;
        this.externalAiApi = externalAiApi;
    }

    @SneakyThrows
    @GetMapping("/sell")
    public void sell() {
        ObjectMapper objectMapper = new ObjectMapper();

        String accessTokenJson = externalTokenApi.getAccessToken(payLoad);
        Token token = objectMapper.readValue(accessTokenJson, Token.class);
        String jsonRequest = """
                {
                  "model": "GigaChat",
                  "messages": [
                    {
                      "role": "user",
                      "content": "Отправь в формате JSON перевод слова кошка"
                    }
                  ],
                  "stream": false,
                  "update_interval": 0
                }
                """;

        System.out.println(externalAiApi.getAnswer(jsonRequest, token.getAccessToken()));

    }


//    @GetMapping("/cool")
//    @SneakyThrows
//    public void cool() {
//        SSLContext sslContext = aiConfiguration.getSslContext();
//
//
//        Unirest.config()
//                .verifySsl(true)
//                .sslContext(sslContext);
//
//
//        kong.unirest.core.HttpResponse
//                <String> httpResponse = Unirest.post("https://gigachat.devices.sberbank.ru/api/v1/chat/completions")
//                .header("Content-Type", "application/json")
//                .header("Accept", "application/json")
//                .header("Authorization", "Bearer " + aiConfiguration.getAccessToken())
//                .body("{\n  \"model\": \"GigaChat\",\n  \"messages\": [\n    {\n      \"role\": \"user\",\n      \"content\": \"Отправь в формате JSON перевод слова кошка\"\n    }\n  ],\n  \"stream\": false,\n  \"update_interval\": 0\n}")
//                .asString();
//        System.out.println(httpResponse.getBody());
//    }

}
