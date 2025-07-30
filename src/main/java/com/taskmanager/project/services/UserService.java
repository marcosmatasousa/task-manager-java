package com.taskmanager.project.services;

import com.taskmanager.project.exceptions.EmailAlreadyInUseException;
import com.taskmanager.project.exceptions.NotFoundException;
import com.taskmanager.project.models.User;
import com.taskmanager.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(User user) {
        if (existsByEmail(user.getEmail())) {
            throw new EmailAlreadyInUseException("E-mail already in use");
        }
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("User not found"));
    }
}
