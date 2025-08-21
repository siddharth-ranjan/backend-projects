package com.hackathon.workouttracker.workout;

import com.hackathon.workouttracker.workout.dto.SessionExerciseDTO;
import com.hackathon.workouttracker.workout.dto.TemplateExerciseDTO;
import com.hackathon.workouttracker.workout.dto.WorkoutSessionDTO;
import com.hackathon.workouttracker.workout.dto.WorkoutTemplateDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WorkoutMapper {

    public WorkoutTemplateDTO toDto(WorkoutTemplate entity) {
        WorkoutTemplateDTO dto = new WorkoutTemplateDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        if (entity.getTemplateExercises() != null) {
            dto.setTemplateExercises(entity.getTemplateExercises().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public TemplateExerciseDTO toDto(TemplateExercise entity) {
        TemplateExerciseDTO dto = new TemplateExerciseDTO();
        dto.setExerciseId(entity.getExercise().getId());
        dto.setExerciseName(entity.getExercise().getName());
        dto.setTargetSets(entity.getTargetSets());
        dto.setTargetReps(entity.getTargetReps());
        dto.setTargetWeightKg(entity.getTargetWeightKg());
        return dto;
    }

    public WorkoutSessionDTO toDto(WorkoutSession entity) {
        WorkoutSessionDTO dto = new WorkoutSessionDTO();
        dto.setId(entity.getId());
        if (entity.getWorkoutTemplate() != null) {
            dto.setTemplateId(entity.getWorkoutTemplate().getId());
        }
        dto.setSessionDate(entity.getSessionDate());
        dto.setStatus(entity.getStatus());
        dto.setNotes(entity.getNotes());
        if (entity.getSessionExercises() != null) {
            dto.setSessionExercises(entity.getSessionExercises().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public SessionExerciseDTO toDto(SessionExercise entity) {
        SessionExerciseDTO dto = new SessionExerciseDTO();
        dto.setExerciseId(entity.getExercise().getId());
        dto.setExerciseName(entity.getExercise().getName());
        dto.setCompletedSets(entity.getCompletedSets());
        dto.setCompletedReps(entity.getCompletedReps());
        dto.setCompletedWeightKg(entity.getCompletedWeightKg());
        return dto;
    }
}
