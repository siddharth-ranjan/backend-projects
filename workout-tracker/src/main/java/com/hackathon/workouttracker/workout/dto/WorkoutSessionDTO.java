package com.hackathon.workouttracker.workout.dto;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutSessionDTO {
    private Long id;
    private Long templateId;
    private LocalDateTime sessionDate;
    private String status;
    private String notes;
    private List<SessionExerciseDTO> sessionExercises;

    public WorkoutSessionDTO() {
    }

    public WorkoutSessionDTO(Long id, Long templateId, LocalDateTime sessionDate, String status, String notes, List<SessionExerciseDTO> sessionExercises) {
        this.id = id;
        this.templateId = templateId;
        this.sessionDate = sessionDate;
        this.status = status;
        this.notes = notes;
        this.sessionExercises = sessionExercises;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public LocalDateTime getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDateTime sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<SessionExerciseDTO> getSessionExercises() {
        return sessionExercises;
    }

    public void setSessionExercises(List<SessionExerciseDTO> sessionExercises) {
        this.sessionExercises = sessionExercises;
    }
}
