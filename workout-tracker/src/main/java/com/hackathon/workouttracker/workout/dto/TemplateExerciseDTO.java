package com.hackathon.workouttracker.workout.dto;

public class TemplateExerciseDTO {

    private Long exerciseId;
    private String exerciseName;
    private int targetSets;
    private int targetReps;
    private double targetWeightKg;

    public TemplateExerciseDTO() {
    }

    public TemplateExerciseDTO(Long exerciseId, String exerciseName, int targetSets, int targetReps, double targetWeightKg) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.targetSets = targetSets;
        this.targetReps = targetReps;
        this.targetWeightKg = targetWeightKg;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getTargetSets() {
        return targetSets;
    }

    public void setTargetSets(int targetSets) {
        this.targetSets = targetSets;
    }

    public int getTargetReps() {
        return targetReps;
    }

    public void setTargetReps(int targetReps) {
        this.targetReps = targetReps;
    }

    public double getTargetWeightKg() {
        return targetWeightKg;
    }

    public void setTargetWeightKg(double targetWeightKg) {
        this.targetWeightKg = targetWeightKg;
    }
}
