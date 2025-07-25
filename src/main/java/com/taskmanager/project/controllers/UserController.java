package com.taskmanager.project.controllers;

import com.taskmanager.project.models.User;
import com.taskmanager.project.models.UserDTO;
import com.taskmanager.project.models.SignUpDTO;
import com.taskmanager.project.services.UserService;
import com.taskmanager.project.utils.PasswordUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "Create new user")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid SignUpDTO userSignUp) {
        if (userService.existsByEmail(userSignUp.getEmail())) {;
            return ResponseEntity.badRequest().body("E-mail already in use");
        }

        String encryptedPassword = PasswordUtil.encrypt(userSignUp.getPassword());

        User newUser = new User(
                userSignUp.getEmail(),
                userSignUp.getUsername(),
                encryptedPassword
        );

        User saved = userService.createNewUser(newUser);
        UserDTO userDTO = new UserDTO(saved);
        return ResponseEntity.status(201).body(userDTO);
    }

    @Operation(description = "Get list of users")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))
            ),
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public List<UserDTO> getAll() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(UserDTO::new).toList();
    }
}
