package com.taskmanager.project.controllers;

import com.taskmanager.project.models.*;
import com.taskmanager.project.repository.TaskRepository;
import com.taskmanager.project.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/todos")
public class TaskController {
    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskController(TaskRepository taskRepo, UserRepository userRepository) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepository;
    }

    @Operation(description = "Create new Task")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Task.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody @Valid NewTaskDTO newTaskRequest, HttpServletRequest request) {
        String stringId = (String) request.getAttribute("userId");
        Long userId = Long.parseLong(stringId);
        Status status = newTaskRequest.getStatus();
        String description = newTaskRequest.getDescription();

        User user = userRepo.getById(userId);
        Task newTask = new Task(user, description, status);
        taskRepo.save(newTask);
        return ResponseEntity.status(201).body(newTask);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @Operation(description = "Get Task list")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Task.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))
            )
    })
    public ResponseEntity<?> getTaskList(HttpServletRequest request,
                                         @Parameter(description = "Status of the tasks", example = "DONE",
                                                 schema = @Schema(allowableValues = {"PENDING", "DONE", "IN_PROGRESS"}))
                                         @RequestParam(required = false) String status) {
        String stringId = (String) request.getAttribute("userId");
        Long userId = Long.parseLong(stringId);

        if (status == null || status.isBlank()) {
            return ResponseEntity.ok(taskRepo.findByUserId(userId));
        }

        try {
            Status statusEnum = Status.valueOf(status.toUpperCase());
            List<Task> tasks = taskRepo.findByUserIdAndStatus(userId, statusEnum);
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status " + status);
        }
    }

    @Operation(description = "Update Task Status")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404",
                    description = "ToDo not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping
    public ResponseEntity<?> updateStatus(@RequestBody TaskStatusUpdateDTO dto) {
        try {
            Status newStatus = Status.valueOf(dto.getStatus().toUpperCase());
            Optional<Task> optionalTask = taskRepo.findById(dto.getId());

            if (optionalTask.isEmpty()) {
                return ResponseEntity.status(404).body("To do not found");
            }

            Task todo = optionalTask.get();
            todo.setStatus(newStatus);
            taskRepo.save(todo);

            return ResponseEntity.ok().body(todo);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status: " + dto.getStatus());
        }
    }
}
