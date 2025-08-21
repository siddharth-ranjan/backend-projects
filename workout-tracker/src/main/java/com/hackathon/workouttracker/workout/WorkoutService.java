package com.hackathon.workouttracker.workout;

import com.hackathon.workouttracker.user.UserEntity;
import com.hackathon.workouttracker.workout.dto.WorkoutSessionDTO;
import com.hackathon.workouttracker.workout.dto.WorkoutTemplateDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkoutService {

    private final WorkoutTemplateRepository templateRepository;
    private final WorkoutSessionRepository sessionRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutMapper workoutMapper;

    public WorkoutService(WorkoutTemplateRepository templateRepository,
                          WorkoutSessionRepository sessionRepository,
                          ExerciseRepository exerciseRepository,
                          WorkoutMapper workoutMapper) {
        this.templateRepository = templateRepository;
        this.sessionRepository = sessionRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutMapper = workoutMapper;
    }

    private UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<WorkoutTemplateDTO> getWorkoutTemplatesForCurrentUser() {
        return templateRepository.findByUserId(getCurrentUser().getId()).stream()
                .map(workoutMapper::toDto)
                .collect(Collectors.toList());
    }

    public WorkoutTemplateDTO getTemplateById(Long id) {
        return templateRepository.findById(id)
                .map(workoutMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Template not found with id: " + id));
    }

    public WorkoutTemplateDTO createWorkoutTemplate(WorkoutTemplateDTO dto) {
        WorkoutTemplate template = new WorkoutTemplate();
        template.setName(dto.getName());
        template.setDescription(dto.getDescription());
        template.setUser(getCurrentUser());
        return workoutMapper.toDto(templateRepository.save(template));
    }

    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }

    public List<WorkoutSessionDTO> getWorkoutSessionsForCurrentUser() {
        return sessionRepository.findByUserId(getCurrentUser().getId()).stream()
                .map(workoutMapper::toDto)
                .collect(Collectors.toList());
    }

    public WorkoutSessionDTO getSessionById(Long id) {
        return sessionRepository.findById(id)
                .map(workoutMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id: " + id));
    }

    public WorkoutSessionDTO createWorkoutSession(WorkoutSessionDTO dto) {
        WorkoutSession session = new WorkoutSession();
        session.setSessionDate(dto.getSessionDate());
        session.setStatus(dto.getStatus());
        session.setNotes(dto.getNotes());
        session.setUser(getCurrentUser());
        if (dto.getTemplateId() != null) {
            WorkoutTemplate template = templateRepository.findById(dto.getTemplateId())
                    .orElseThrow(() -> new EntityNotFoundException("Template not found"));
            session.setWorkoutTemplate(template);
        }
        return workoutMapper.toDto(sessionRepository.save(session));
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }
}