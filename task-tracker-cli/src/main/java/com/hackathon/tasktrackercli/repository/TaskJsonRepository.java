package com.hackathon.tasktrackercli.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hackathon.tasktrackercli.model.Task;
import com.hackathon.tasktrackercli.model.TaskStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TaskJsonRepository {

    @Value("${task.storage.path}")
    private String filePath;

    private final ObjectMapper objectMapper;
    private List<Task> tasks;
    private AtomicInteger maxId = new AtomicInteger(0);

    public TaskJsonRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @PostConstruct
    public void init() {
        loadTasks();
    }

    private void loadTasks() {
        File file = new File(filePath);
        try {
            if (file.exists() && file.length() > 0) {
                this.tasks = objectMapper.readValue(file, new TypeReference<>() {});
                int currentMaxId = 0;
                for(Task task : this.tasks) {
                    currentMaxId = Math.max(currentMaxId, task.getId());
                }
                this.maxId.set(currentMaxId);
            } else {
                this.tasks = new ArrayList<>();
            }
        }
        catch (Exception e) {
            System.err.println("Error loading tasks from file: " + e.getMessage());
            this.tasks = new ArrayList<>();
        }
    }

    public Task saveTask(String description) {
        Task task = new Task(this.maxId.incrementAndGet(), description);
        tasks.add(task);
        saveTasks();
        return task;
    }

    private void saveTasks() {
        try {
            // Write the current list of tasks back to the file
            objectMapper.writeValue(new File(filePath), tasks);
        } catch (IOException e) {
            // A more robust application would handle this error more gracefully
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public Task updateTask(int index, String description) {
        Task task = findById(index);
        if(task != null) {
            task.setDescription(description);
            saveTasks();
        } else {
            System.err.println("Task with id " + index + " not found");
        }
        return task;
    }

    public Task findById(int id) {
        for(Task task : tasks) {
            if(task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public boolean deleteTask(int id) {
        Task task = findById(id);
        if(task != null) {
            tasks.remove(task);
            saveTasks();
            return true;
        } else {
            System.err.println("Task with id " + id + " not found");
            return false;
        }

    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public List<Task> getAllTasksByStatus(TaskStatus status) {
        List<Task> resultTasks = new ArrayList<>();
        for(Task task : tasks) {
            if(task.getStatus() == status) {
                resultTasks.add(task);
            }
        }
        return resultTasks;
    }

    public Task updateStatus(int id, TaskStatus status) {
        Task task = findById(id);
        if(task != null) {
            task.setStatus(status);
            task.setUpdatedAt();
            saveTasks();
        } else {
            System.err.println("Task with id " + id + " not found");
        }
        return task;
    }

}
