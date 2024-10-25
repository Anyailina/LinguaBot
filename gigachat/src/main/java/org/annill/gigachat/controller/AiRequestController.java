package org.annill.gigachat.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.annill.gigachat.service.AiRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@AllArgsConstructor
@Slf4j
public class AiRequestController {
    private final AiRequestService aiRequestService;

    @SneakyThrows
    @GetMapping("request")
    public void sell() {
        log.info(aiRequestService.getAnswer("cat"));
    }
}
