package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.annill.linguabot.model.AiModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class AiService {
    private final RestTemplate wordSuggestionTemplate;
    @Value("${http.ai-request}")
    private String addressAiRequest;

    @SneakyThrows
    public String getWords(String word) {
        AiModel aiModel = new AiModel(word);
        return wordSuggestionTemplate.postForObject(addressAiRequest, aiModel, String.class);

    }
}
