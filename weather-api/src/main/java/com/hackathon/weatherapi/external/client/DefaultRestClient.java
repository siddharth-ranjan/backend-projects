package com.hackathon.weatherapi.external.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class DefaultRestClient {

    @Value("${weather.api-key}")
    private String apiKey;

    @Value("${weather.base-url}")
    private String baseUrl;

    private final RedisTemplate<String, Map<String, Object>> redisTemplate;

    public DefaultRestClient(RedisTemplate<String, Map<String, Object>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Map getWeather(String location) {
        URI uri = URI.create(baseUrl + location + "?unitGroup=metric&include=days&key=" + apiKey + "&contentType=json");

        String redisKey = "weather:"+ location;

        ValueOperations<String, Map<String, Object>> ops = redisTemplate.opsForValue();
        Map weatherData = (Map) ops.get(redisKey);

        if (weatherData == null) {
            RestClient client = RestClient.create(uri);
            weatherData = client.get().retrieve().body(Map.class);
            assert weatherData != null;
            ops.set(redisKey, weatherData, 5, TimeUnit.SECONDS);
        } else {
            System.out.println("Caching from redis");
        }
        return weatherData;

    }
}
