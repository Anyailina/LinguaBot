package org.annill.gigachat.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.annill.gigachat.ExternalAiFeignClient;
import org.annill.gigachat.dto.MessageDto;
import org.annill.gigachat.dto.RequestAiDto;
import org.annill.gigachat.enums.Model;
import org.annill.gigachat.enums.Role;
import org.annill.gigachat.parsing.ChatCompletion;
import org.annill.gigachat.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiRequestService {
    private final AiTokenService aiTokenService;
    private final ExternalAiFeignClient externalAiApi;
    private final Utils utils;
    @Value("${prompt.translate}")
    private String promptTranslate;

    @SneakyThrows
    public String getAnswer(String word) {
        String token = aiTokenService.getTokenService();
        String requestString = String.format(promptTranslate, word);

        List<MessageDto> messages = List.of(new MessageDto(requestString, Role.user));
        RequestAiDto requestAi = new RequestAiDto(Model.GigaChat, messages, false, 0);

        String answerFromAi = externalAiApi.getAnswer(requestAi, token);
        ChatCompletion chatCompletion = utils.getObjectMapper().readValue(answerFromAi, ChatCompletion.class);
        ChatCompletion.Choice choice = chatCompletion.getChoices()
                .stream()
                .findFirst()
                .orElseThrow();

        return choice.getMessage().getContent();
    }
}
