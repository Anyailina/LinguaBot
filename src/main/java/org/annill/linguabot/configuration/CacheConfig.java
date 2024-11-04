package org.annill.linguabot.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {
    @Value("${cache.name}")
    private String cacheName;
    @Value("${cache.ttl.minutes}")
    private long cacheTtlMinutes;


    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

//    @Bean
//    public RedisTemplate<Long, UserCacheData> sessionRedisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<Long, UserCacheData> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        return template;
//    }
//
//
//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration() {
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(cacheTtlMinutes))
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//    }

//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        return RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(redisCacheConfiguration())
//                .build();
//    }

    @Bean
    public Cache getCache(CacheManager cacheManager) {
        return cacheManager.getCache(cacheName);
    }

}