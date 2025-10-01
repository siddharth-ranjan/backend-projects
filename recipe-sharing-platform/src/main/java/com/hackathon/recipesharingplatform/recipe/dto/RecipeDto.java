package com.hackathon.recipesharingplatform.recipe.dto;

import com.hackathon.recipesharingplatform.auth.dto.ChefDto;
import lombok.Data;

@Data
public class RecipeDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private ChefDto chef;
}