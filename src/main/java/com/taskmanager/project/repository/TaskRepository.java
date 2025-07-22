package com.taskmanager.project.repository;

import com.taskmanager.project.models.Status;
import com.taskmanager.project.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndStatus(Long userId, Status status);
}
