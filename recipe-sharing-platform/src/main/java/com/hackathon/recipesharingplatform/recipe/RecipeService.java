package com.hackathon.recipesharingplatform.recipe;

import com.hackathon.recipesharingplatform.auth.ChefRepository;
import com.hackathon.recipesharingplatform.auth.dto.ChefDto;
import com.hackathon.recipesharingplatform.auth.model.Chef;
import com.hackathon.recipesharingplatform.recipe.dto.RecipeDto;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final Path rootLocation;
    private final ChefRepository chefRepository;

    public RecipeService(RecipeRepository recipeRepository, @Value("${app.upload.dir:${user.home}}") String uploadDir, ChefRepository chefRepository) {
        this.recipeRepository = recipeRepository;
        this.chefRepository = chefRepository;
        this.rootLocation = Paths.get(uploadDir);
    }

    public void saveRecipe(String title, String description, MultipartFile image, String chefName) throws IOException {
        if (image.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }
        Chef chef = chefRepository.findByUsername(chefName)
                .orElseThrow(() -> new UsernameNotFoundException("Chef not found with username: " + chefName));

        String originalFilename = image.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFilename))
                .normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new IOException("Cannot store file outside current directory.");
        }

        Files.createDirectories(this.rootLocation);

        Thumbnails.of(image.getInputStream())
                .size(200, 200)
                .toFile(destinationFile.toFile());

        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setImageUrl("/uploads/" + uniqueFilename);
        recipe.setPublishedDate(LocalDate.now());
        recipe.setChef(chef);

        recipeRepository.save(recipe);
    }

    public Page<RecipeDto> getAllRecipes(Pageable pageable) {
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);
        return recipePage.map(this::mapToDto);
    }

    public RecipeDto getRecipeByTitle(String title) {
        Recipe recipe = recipeRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        return mapToDto(recipe);
    }

    public List<RecipeDto> getRecipeByPublishedDate(LocalDate date) {
        return recipeRepository.findByPublishedDate(date).stream().map(this::mapToDto).toList();
    }

    public List<RecipeDto> getRecipeByChef(String username) {
        Chef chef = chefRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Chef not found with username: " + username));

        return recipeRepository.findByChef(chef).stream().map(this::mapToDto).toList();
    }

    private RecipeDto mapToDto(Recipe recipe) {
        ChefDto chefDto = new ChefDto(
                recipe.getChef().getId(),
                recipe.getChef().getUsername()
        );

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setTitle(recipe.getTitle());
        recipeDto.setDescription(recipe.getDescription());
        recipeDto.setImageUrl(recipe.getImageUrl());
        recipeDto.setChef(chefDto);

        return recipeDto;
    }
}