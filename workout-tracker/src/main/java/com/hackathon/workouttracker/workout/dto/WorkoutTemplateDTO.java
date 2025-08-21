package com.hackathon.workouttracker.workout.dto;

import java.util.List;

public class WorkoutTemplateDTO {
    private Long id;
    private String name;
    private String description;
    private List<TemplateExerciseDTO> templateExercises;

    public WorkoutTemplateDTO() {
    }

    public WorkoutTemplateDTO(Long id, String name, String description, List<TemplateExerciseDTO> templateExercises) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public List<TemplateExerciseDTO> getTemplateExercises() {
        return templateExercises;
    }

    public void setTemplateExercises(List<TemplateExerciseDTO> templateExercises) {
        this.templateExercises = templateExercises;
    }
}
