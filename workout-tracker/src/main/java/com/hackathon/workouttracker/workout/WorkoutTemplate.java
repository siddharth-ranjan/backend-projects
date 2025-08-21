package com.hackathon.workouttracker.workout;

import com.hackathon.workouttracker.user.UserEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class WorkoutTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemplateExercise> templateExercises;

    public WorkoutTemplate() {
    }

    public WorkoutTemplate(Long id, String name, String description, UserEntity user, List<TemplateExercise> templateExercises) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.templateExercises = templateExercises;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<TemplateExercise> getTemplateExercises() {
        return templateExercises;
    }

    public void setTemplateExercises(List<TemplateExercise> templateExercises) {
        this.templateExercises = templateExercises;
    }
}
