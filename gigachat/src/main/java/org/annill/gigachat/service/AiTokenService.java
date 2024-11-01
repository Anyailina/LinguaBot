package org.annill.gigachat.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.annill.gigachat.ExternalTokenFeignClient;
import org.annill.gigachat.parsing.Token;
import org.annill.gigachat.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AiTokenService {
    private final ExternalTokenFeignClient externalTokenApi;
    private final Utils utils;
    private final CacheManager cacheManager;
    @Value("${ai.payload}")
    private String payLoad;

    @SneakyThrows
    @Cacheable(value = "ai-token", key = "'token'")
    public String getTokenService() {
        String accessTokenJson = externalTokenApi.getAccessToken(payLoad);
        Token token = utils.getObjectMapper().readValue(accessTokenJson, Token.class);
        long ttl = token.getExpires();

        scheduleCacheEviction("ai-token", "token", ttl);
        return token.getAccessToken();
    }

    private void scheduleCacheEviction(String cacheName, String key, long ttl) {
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.evict(key);
            }
        }, ttl, TimeUnit.SECONDS);
    }
}

