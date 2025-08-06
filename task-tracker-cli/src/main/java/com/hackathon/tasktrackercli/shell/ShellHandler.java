package com.hackathon.tasktrackercli.shell;

import com.hackathon.tasktrackercli.model.Task;
import com.hackathon.tasktrackercli.model.TaskStatus;
import com.hackathon.tasktrackercli.service.TaskService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class ShellHandler {

    private final TaskService taskService;

    public ShellHandler(TaskService taskService) {
        this.taskService = taskService;
    }

    @ShellMethod(key = "add", value = "Create new task.")
    public String addTask(
        @ShellOption(help = "Description of the new task.") String description
    ){
        Task task = taskService.addTask(description);
        if(task != null) return "Task added successfully!\n\t" + task.toString();
        else return "Task added failed!";
    }

    @ShellMethod(key = "update", value = "Update description of a task")
    public String updateTask(
            @ShellOption(help = "Provide id of the task") int id,
            @ShellOption(help = "Provide modified description for the task") String description
    ) {
        Task task = taskService.updateTask(id, description);
        if(task != null) return "Task updated successfully!\n\t" + task.toString();
        else return "Task updated failed!";
    }

    @ShellMethod(key = "delete", value = "Delete task by id")
    public String deleteTask(
            @ShellOption(help = "Provide id of the task to be deleted") int id
    ) {
        if(taskService.deleteTask(id))return "Task deleted successfully!";
        else return "Task deleted failed!";
    }

    @ShellMethod(key = "mark", value = "Update status of task by id")
    public String markTask(
            @ShellOption(help = "Provide id of the task") int id,
            @ShellOption(help = "Provide updated status") String status
    ) {
       TaskStatus taskStatus = getTaskStatus(status);
        Task task = taskService.updateStatus(id, taskStatus);
        if(task != null) return "Task status updated successfully!\n\t" + task.toString();
        else return "Invalid Id! Task status updated failed!";
    }

    @ShellMethod(key = "list", value = "List all tasks")
    public String listTasks(
            @ShellOption(help = "Optional: Provide status of tasks", defaultValue = "") String status
    ) {
        TaskStatus taskStatus = getTaskStatus(status);
        if(taskStatus != null || status.isEmpty()) {
            List<Task> tasks;
            if(taskStatus != null) tasks = taskService.getAllTasks(taskStatus);
            else tasks = taskService.getAllTasks();
            StringBuilder tasksString = new StringBuilder();
            for (Task task : tasks) {
                tasksString.append(task.toString()).append("\n");
            }
            return tasksString.toString();
        } else {
            return "Status not found!";
        }

    }

    private TaskStatus getTaskStatus(String status) {
        TaskStatus taskStatus = null;
        switch (status) {
            case "done":
                taskStatus = TaskStatus.DONE;
                break;
            case "todo":
                taskStatus = TaskStatus.TODO;
                break;
            case "in-progress":
                taskStatus = TaskStatus.IN_PROGRESS;
                break;
            default:
                break;
        }
        return taskStatus;
    }
}
