package org.annill.linguabot.feignClient;

import feign.RequestLine;
import org.annill.linguabot.configuration.feignClient.TokenAiFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "aiAuth", configuration = TokenAiFeignConfig.class)
public interface ExternalTokenFeignClient {

    @RequestLine("POST ")
    String getAccessToken(@RequestBody String payLoad);
}
