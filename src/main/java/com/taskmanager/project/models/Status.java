package com.taskmanager.project.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "ToDo status", enumAsRef = true)
public enum Status {
    @Schema(description = "Pending task")
    PENDING,

    @Schema(description = "Task in progress")
    IN_PROGRESS,

    @Schema(description = "Task done")
    DONE;
}
