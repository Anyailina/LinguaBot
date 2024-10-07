package org.annill.linguabot.configuration.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "aiAuth", configuration = AiTokenFeignConfig.class)
public interface ExternalTokenAiApi {

    @PostMapping
    String getAccessToken(@RequestBody String payLoad);

}
