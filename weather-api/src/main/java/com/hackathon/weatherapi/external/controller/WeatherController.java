package com.hackathon.weatherapi.external.controller;

import com.hackathon.weatherapi.external.client.DefaultRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final DefaultRestClient client;

    public WeatherController(DefaultRestClient client) {
        this.client = client;
    }

    @GetMapping
    public Map getWeather() {
        return client.getWeather("Patna,India");
    }

}
