package com.taskmanager.project.services;

import com.taskmanager.project.exceptions.NotFoundException;
import com.taskmanager.project.models.User;
import com.taskmanager.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Bad credentials"));
    }
}
