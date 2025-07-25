package com.taskmanager.project.repository;

import com.taskmanager.project.models.Status;
import com.taskmanager.project.models.Task;
import com.taskmanager.project.models.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should find Task by user id")
    void findByUserIdCase1() {
        User user = new User("test@gmail.com", "john.doe", "password123");
        entityManager.persist(user);

        Task task = new Task(user, "Any description", Status.DONE);
        entityManager.persist(task);

        List<Task> result = taskRepository.findByUserId(user.getId());
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.getFirst() == task).isTrue();
    }

    @Test
    @DisplayName("Should not find Task by user id")
    void findByUserIdCase2() {
        User user = new User("test@gmail.com", "john.doe", "password123");
        entityManager.persist(user);

        List<Task> result = taskRepository.findByUserId(user.getId());
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should find Task by userId and status")
    void findByUserIdAndStatusCase1() {
        User user = new User("test@gmail.com", "john.doe", "password123");
        entityManager.persist(user);

        Status status = Status.DONE;
        Task task = new Task(user, "Any description", status);
        entityManager.persist(task);

        List<Task> result = taskRepository.findByUserIdAndStatus(user.getId(), status);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.getFirst() == task).isTrue();
    }

    @Test
    @DisplayName("Should not find Task by userId and status")
    void findByUserIdAndStatusCase2() {
        User user = new User("test@gmail.com", "john.doe", "password123");
        entityManager.persist(user);

        Status status = Status.DONE;
        Task task = new Task(user, "Any description", status);
        entityManager.persist(task);

        List<Task> result = taskRepository.findByUserIdAndStatus(user.getId(), null);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should not find Task by userId and status")
    void findByUserIdAndStatusCase3() {
        User user = new User("test@gmail.com", "john.doe", "password123");
        entityManager.persist(user);

        List<Task> result = taskRepository.findByUserIdAndStatus(user.getId(), null);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should not find Task by userId and status")
    void findByUserIdAndStatusCase4() {

        Long nonExistentId = 99L;
        List<Task> result = taskRepository.findByUserIdAndStatus(nonExistentId, null);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should find Task by id")
    void findByIdCase1() {
        User user = new User("test@gmail.com", "john.doe", "password123");
        entityManager.persist(user);

        Task task = new Task(user, "Any description", Status.DONE);
        entityManager.persist(task);
        Optional<Task> result = taskRepository.findById(task.getId());

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not find Task by id")
    void findByIdCase2() {
        Long nonExistentId = 1L;
        Optional<Task> result = taskRepository.findById(nonExistentId);
        assertThat(result.isEmpty()).isTrue();
    }
}
