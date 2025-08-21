package com.hackathon.workouttracker.workout.dto;

public class SessionExerciseDTO {
    private Long exerciseId;
    private String exerciseName;
    private int completedSets;
    private String completedReps;
    private double completedWeightKg;

    public SessionExerciseDTO() {
    }

    public SessionExerciseDTO(Long exerciseId, String exerciseName, int completedSets, String completedReps, double completedWeightKg) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.completedSets = completedSets;
        this.completedReps = completedReps;
        this.completedWeightKg = completedWeightKg;
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

    public int getCompletedSets() {
        return completedSets;
    }

    public void setCompletedSets(int completedSets) {
        this.completedSets = completedSets;
    }

    public String getCompletedReps() {
        return completedReps;
    }

    public void setCompletedReps(String completedReps) {
        this.completedReps = completedReps;
    }

    public double getCompletedWeightKg() {
        return completedWeightKg;
    }

    public void setCompletedWeightKg(double completedWeightKg) {
        this.completedWeightKg = completedWeightKg;
    }
}
