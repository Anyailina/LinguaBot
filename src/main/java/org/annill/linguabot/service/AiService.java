package org.annill.linguabot.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.annill.linguabot.feignClient.AiFeignClient;
import org.annill.linguabot.model.AiModel;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AiService {
    private final AiFeignClient aiFeignClient;

    @SneakyThrows
    public String getWords(String word) {
        AiModel aiModel = new AiModel(word);
        return aiFeignClient.getAnswer(aiModel);
    }
}
