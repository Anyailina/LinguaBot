package org.annill.gigachat;


import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.annill.gigachat.configuration.RequestFeignConfig;
import org.annill.gigachat.dto.RequestAiDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai", configuration = RequestFeignConfig.class)
public interface ExternalAiFeignClient {
    @Headers("Authorization: Bearer {token}")
    @RequestLine("POST /api/v1/chat/completions")
    String getAnswer(@RequestBody RequestAiDto request, @Param("token") String token);
}
