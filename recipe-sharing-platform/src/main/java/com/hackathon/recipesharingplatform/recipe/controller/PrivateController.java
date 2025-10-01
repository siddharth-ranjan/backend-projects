package com.hackathon.recipesharingplatform.recipe.controller;

import com.hackathon.recipesharingplatform.recipe.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/recipe/create")
public class PrivateController {

    private final RecipeService recipeService;

    public PrivateController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("")
    public ResponseEntity<String> saveRecipe(@RequestParam String title, @RequestParam String description, @RequestParam MultipartFile image, Principal principal) {
        try {
            recipeService.saveRecipe(title, description, image, principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body("Recipe created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create recipe: " + e.getMessage());
        }
    }
}
