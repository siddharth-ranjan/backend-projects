package com.hackathon.recipesharingplatform.recipe.controller;

import com.hackathon.recipesharingplatform.recipe.RecipeService;
import com.hackathon.recipesharingplatform.recipe.dto.RecipeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/public/recipe")
public class PublicController {

    private final RecipeService recipeService;

    public PublicController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/chef")
    public ResponseEntity<List<RecipeDto>> getRecipeByChef(@RequestParam String chef) {
        try {
            return ResponseEntity.ok().body(recipeService.getRecipeByChef(chef));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<RecipeDto>> getAllRecipes(
            @PageableDefault(size = 1, sort = "id") Pageable pageable
    ) {
        try {
            Page<RecipeDto> recipePage = recipeService.getAllRecipes(pageable);
            return ResponseEntity.ok(recipePage);
        }  catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/publicationDate")
    public ResponseEntity<List<RecipeDto>> getRecipeByPublicationDate(@RequestParam LocalDate publicationDate) {
        try {
            return ResponseEntity.ok().body(recipeService.getRecipeByPublishedDate(publicationDate));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/title")
    public ResponseEntity<RecipeDto> getRecipeByTitle(@RequestParam String title) {
        try {
            return ResponseEntity.ok().body(recipeService.getRecipeByTitle(title));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
