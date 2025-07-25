package com.taskmanager.project.services;

import com.taskmanager.project.models.Status;
import com.taskmanager.project.models.Task;
import com.taskmanager.project.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createNewTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getTasksByUserIdAndStatus(Long userId, Status status) {
        return taskRepository.findByUserIdAndStatus(userId, status);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateStatus(Task task) {
        return taskRepository.save(task);
    }
}
