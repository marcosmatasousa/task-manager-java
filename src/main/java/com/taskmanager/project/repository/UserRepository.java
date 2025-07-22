package com.taskmanager.project.repository;

import com.taskmanager.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    boolean existsByEmail(String email);
}
