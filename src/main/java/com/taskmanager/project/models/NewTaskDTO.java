package com.taskmanager.project.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NewTaskDTO {
    @NotBlank(message = "Must have a description")
    @Schema(description = "Description of the task", example = "Study Spring")
    private final String description;

    @NotNull(message = "Must have a status")
    @Schema(description = "Current state of the task", example = "PENDING")
    private final Status status;

    public NewTaskDTO(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
