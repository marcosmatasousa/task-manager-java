package com.taskmanager.project.services;

import com.taskmanager.project.exceptions.EmailAlreadyInUseException;
import com.taskmanager.project.exceptions.NotFoundException;
import com.taskmanager.project.models.User;
import com.taskmanager.project.repository.UserRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should get user by id successfully")
    void getByIdCase1() {
        User user = new User("test@gmail.com", "test name", "password123");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getById(1L);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    @DisplayName("Should not get user by id successfully")
    void getByIdCase2() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            userService.getById(1L);
        });
    }

    @Test
    @DisplayName("Should create user successfully")
    void createUserCase1() {
        String email = "test@gmail.com";
        User user = new User(email, "test name", "password123");
        when(userService.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createNewUser(user);

        assertEquals(user, createdUser);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should throw an EmailAlreadyInUseException")
    void createUserCase2() {
        String email = "test@gmail.com";
        User user = new User(email, "test name", "password123");

        when(userService.existsByEmail(email)).thenReturn(true);

        assertThrows(EmailAlreadyInUseException.class, () -> userService.createNewUser(user));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return a list of all users")
    void getAllUsers() {
        User user1 = new User("test1@gmail.com", "test1", "password123");
        User user2 = new User("test2@gmail.com", "test2", "password123");
        List<User> mockUsers = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));

        verify(userRepository).findAll();
    }
}
