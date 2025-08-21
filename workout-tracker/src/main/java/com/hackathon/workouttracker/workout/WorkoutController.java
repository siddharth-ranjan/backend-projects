package com.hackathon.workouttracker.workout;

import com.hackathon.workouttracker.workout.dto.WorkoutSessionDTO;
import com.hackathon.workouttracker.workout.dto.WorkoutTemplateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/templates")
    public ResponseEntity<List<WorkoutTemplateDTO>> getTemplates() {
        return ResponseEntity.ok(workoutService.getWorkoutTemplatesForCurrentUser());
    }

    @PostMapping("/templates")
    public ResponseEntity<WorkoutTemplateDTO> createTemplate(@RequestBody WorkoutTemplateDTO templateDto) {
        return ResponseEntity.ok(workoutService.createWorkoutTemplate(templateDto));
    }

    @GetMapping("/templates/{id}")
    public ResponseEntity<WorkoutTemplateDTO> getTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getTemplateById(id));
    }

    @DeleteMapping("/templates/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        workoutService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<WorkoutSessionDTO>> getSessions() {
        return ResponseEntity.ok(workoutService.getWorkoutSessionsForCurrentUser());
    }

    @PostMapping("/sessions")
    public ResponseEntity<WorkoutSessionDTO> createSession(@RequestBody WorkoutSessionDTO sessionDto) {
        return ResponseEntity.ok(workoutService.createWorkoutSession(sessionDto));
    }

    @GetMapping("/sessions/{id}")
    public ResponseEntity<WorkoutSessionDTO> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getSessionById(id));
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        workoutService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}