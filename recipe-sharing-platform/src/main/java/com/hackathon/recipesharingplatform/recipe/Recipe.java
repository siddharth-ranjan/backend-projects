package com.hackathon.recipesharingplatform.recipe;

import com.hackathon.recipesharingplatform.auth.model.Chef;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data

public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chef_id", nullable = false)
    private Chef chef;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDate publishedDate;

}