package org.annill.linguabot.configuration.feignClient;

import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

;

@FeignClient(name = "ai", configuration = RequestFeignConfig.class)
public interface ExternalAiApi {
    @Headers({"Content-Type: application/json",
            "Accept: application/json",
            "Authorization: Bearer {token}"})
    @PostMapping
    String getAnswer(@RequestBody String request, @Param("token") String token);

}
