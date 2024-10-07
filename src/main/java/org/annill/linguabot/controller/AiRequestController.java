package org.annill.linguabot.controller;

import lombok.SneakyThrows;
import org.annill.linguabot.service.AiRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ai")
public class AiRequestController {
    private final AiRequestService aiRequestService;

    public AiRequestController(AiRequestService aiRequestService) {
        this.aiRequestService = aiRequestService;
    }

    @PostMapping("request/")
    @SneakyThrows
    public void sell(@RequestParam String request) {
        System.out.println(aiRequestService.getAnswer(request));
    }
}
