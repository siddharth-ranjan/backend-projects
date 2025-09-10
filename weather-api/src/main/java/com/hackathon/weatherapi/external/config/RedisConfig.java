package com.hackathon.weatherapi.external.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Map<String, Object>> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Map<String, Object>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Use StringRedisSerializer for keys to ensure they are human-readable in Redis.
        template.setKeySerializer(new StringRedisSerializer());

        // Use GenericJackson2JsonRedisSerializer for values to store and retrieve complex objects (like Maps) as JSON.
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}