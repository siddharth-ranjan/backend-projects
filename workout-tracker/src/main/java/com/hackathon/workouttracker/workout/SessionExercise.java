package com.hackathon.workouttracker.workout;

import jakarta.persistence.*;

@Entity
public class SessionExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private WorkoutSession session;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    private int completedSets;
    private String completedReps;
    private double completedWeightKg;

    public SessionExercise() {
    }

    public SessionExercise(Long id, WorkoutSession session, Exercise exercise, int completedSets, String completedReps, double completedWeightKg) {
        this.id = id;
        this.session = session;
        this.exercise = exercise;
        this.completedSets = completedSets;
        this.completedReps = completedReps;
        this.completedWeightKg = completedWeightKg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutSession getSession() {
        return session;
    }

    public void setSession(WorkoutSession session) {
        this.session = session;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
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
