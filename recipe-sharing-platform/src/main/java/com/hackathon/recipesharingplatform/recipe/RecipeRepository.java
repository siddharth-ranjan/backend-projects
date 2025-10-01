package com.hackathon.recipesharingplatform.recipe;

import com.hackathon.recipesharingplatform.auth.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByTitle(String title);

    List<Recipe> findByPublishedDate(LocalDate publishedDate);

    List<Recipe> findByChef(Chef chef);
}