package com.hackathon.githubuseractivity.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubEvent(
        String id,
        String type,
        Actor actor,
        Repo repo,
        Map<String, Object> payload,
        @JsonProperty("created_at")
        String createdAt
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Actor(String login) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Repo(String name) {}
}
