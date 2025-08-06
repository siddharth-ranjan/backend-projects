package com.hackathon.tasktrackercli.service;

import com.hackathon.tasktrackercli.model.Task;
import com.hackathon.tasktrackercli.model.TaskStatus;
import com.hackathon.tasktrackercli.repository.TaskJsonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskJsonRepository taskJsonRepository;

    public TaskService(TaskJsonRepository taskJsonRepository) {
        this.taskJsonRepository = taskJsonRepository;
    }

    public List<Task> getAllTasks() {
        return taskJsonRepository.getAllTasks();
    }

    public List<Task> getAllTasks(TaskStatus status) {
        return new ArrayList<>(taskJsonRepository.getAllTasksByStatus(status));
    }

    public boolean deleteTask(int id) {
        return taskJsonRepository.deleteTask(id);
    }

    public Task addTask(String description) {
        return taskJsonRepository.saveTask(description);
    }

    public Task updateTask(int id, String description) {
        return taskJsonRepository.updateTask(id, description);
    }

    public Task updateStatus(int id, TaskStatus status) {
        return taskJsonRepository.updateStatus(id, status);
    }

}
