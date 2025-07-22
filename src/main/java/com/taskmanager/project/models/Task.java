package com.taskmanager.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "It is necessary to have a status")
    private Status status;

    @NotBlank(message = "It is necessary to have a description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public Task() {}

    public Task(User user, String description, Status status) {
        this.user = user;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
