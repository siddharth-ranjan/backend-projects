package com.hackathon.recipesharingplatform.auth.dto;

import lombok.Data;

@Data
public class ChefDto {
    private Long id;
    private String username;

    public ChefDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}