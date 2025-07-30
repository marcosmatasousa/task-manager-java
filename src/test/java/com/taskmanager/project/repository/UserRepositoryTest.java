package com.taskmanager.project.repository;

import com.taskmanager.project.models.SignUpDTO;
import com.taskmanager.project.models.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should find User by email successfully")
    void findUserByEmailCase1() {
        String email = "teste@gmail.com";
        User user = new User(email, "johndoe", "12345678");
        entityManager.persist(user);

        Optional<User> result = userRepository.findByEmail(email);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not find User by email")
    void findUserByEmailCase2() {
        String email = "teste@gmail.com";
        Optional<User> result = userRepository.findByEmail(email);
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should return true if User exists by email")
    void userExistsByEmailCase1() {
        String email = "teste@gmail.com";
        User user = new User(email, "johndoe", "12345678");
        entityManager.persist(user);

        boolean result = userRepository.existsByEmail(email);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should return false if User does not exists by email")
    void userExistsByIdCase2() {
        String email = "teste@gmail.com";
        boolean result = userRepository.existsByEmail(email);
        assertThat(result).isFalse();
    }

    private User createUser(SignUpDTO dto){
        User newUser = new User(dto);
        this.entityManager.persist(newUser);
        return newUser;
    }
}
