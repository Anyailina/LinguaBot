package org.annill.linguabot.feignClient;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.annill.linguabot.configuration.feignClient.RequestFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai", configuration = RequestFeignConfig.class)
public interface ExternalAiFeignClient {
    @Headers("Authorization: Bearer {token}")
    @RequestLine("POST ")
    String getAnswer(@RequestBody String request, @Param("token") String token);
}
