package com.taskmanager.project.services;

import com.taskmanager.project.exceptions.NotFoundException;
import com.taskmanager.project.models.Status;
import com.taskmanager.project.models.Task;
import com.taskmanager.project.models.User;
import com.taskmanager.project.repository.TaskRepository;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @Test
    @DisplayName("Should create new Task successfully")
    void createNewTask() {
        User user = new User("test@gmail.com", "test name", "password123");
        Task task = new Task(user, "test description", any());
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createNewTask(task);

        assertEquals(result, task);
        verify(taskRepository).save(task);
    }

    @Test
    @DisplayName("Should return the list of tasks by user id")
    void getTasksByUserId() {
        User user = new User("test@gmail.com", "test name", "password123");
        Task task1 = new Task(user, "test description", null);
        Task task2 = new Task(user, "test description", null);
        List<Task> mockTasks = List.of(task1, task2);

        when(taskRepository.findByUserId(user.getId())).thenReturn(mockTasks);

        List<Task> result = taskService.getTasksByUserId(user.getId());

        assertEquals(2, result.size());
        assertTrue(result.contains(task1));
        assertTrue(result.contains(task2));

        verify(taskRepository).findByUserId(user.getId());
    }

    @Test
    @DisplayName("Should return the list of tasks by user id and status")
    void getTasksByUserIdAndStatusCase1() {
        User user = new User("test@gmail.com", "test name", "password123");
        Status status = Status.PENDING;
        Task task1 = new Task(user, "test description", status);
        Task task2 = new Task(user, "test description", status);
        List<Task> mockTasks = List.of(task1, task2);

        when(taskRepository.findByUserIdAndStatus(user.getId(), status)).thenReturn(mockTasks);

        List<Task> result = taskService.getTasksByUserIdAndStatus(user.getId(), status);

        assertEquals(2, result.size());
        assertTrue(result.contains(task1));
        assertTrue(result.contains(task2));
        verify(taskRepository).findByUserIdAndStatus(user.getId(), status);
    }

    @Test
    @DisplayName("Should return an empty list")
    void getTasksByUserIdAndStatusCase2() {
        User user = new User("test@gmail.com", "test name", "password123");
        Status status = Status.PENDING;

        when(taskRepository.findByUserIdAndStatus(user.getId(), status)).thenReturn(Collections.emptyList());

        List<Task> result = taskService.getTasksByUserIdAndStatus(user.getId(), status);
        assertEquals(0, result.size());
        verify(taskRepository).findByUserIdAndStatus(user.getId(), status);
    }

    @Test
    @DisplayName("Should return a task by ID")
    void getTaskByIdCase1() {
        User user = new User("test@gmail.com", "test name", "password123");
        Task task = new Task(user, "test description", any());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertEquals(task.getId(), result.getId());
    }

    @Test
    @DisplayName("Should not return a task by ID successfully")
    void getTaskByIdCase2() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            taskService.getTaskById(1L);
        });
    }

    @Test
    @DisplayName("Should update task successfully")
    void updateTask() {
        User user = new User("test@gmail.com", "test name", "password123");
        Task task = new Task(user, "test description", any());

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.updateStatus(task);
        assertEquals(result, task);
        verify(taskRepository).save(task);
    }
}
