package org.annill.linguabot.feignClient;

import org.annill.linguabot.model.AiModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai")
public interface AiFeignClient {
    @PostMapping
    String getAnswer(@RequestBody AiModel aiModel);
}
