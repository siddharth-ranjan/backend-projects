package com.hackathon.githubuseractivity.service;

import com.hackathon.githubuseractivity.record.GithubEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GithubService {

    private final WebClient webClient;

    public GithubService(WebClient.Builder webClientBuilder, @Value("${github.api.baseUrl}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Flux<GithubEvent> getUserActivity(String username) {
        return this.webClient.get()
                .uri("/users/{username}/events", username)
                .retrieve()
                .bodyToFlux(GithubEvent.class)
                .doOnError(throwable -> System.err.println(throwable.getMessage()));
    }
}
