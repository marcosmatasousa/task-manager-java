package com.taskmanager.project.controllers;
import com.taskmanager.project.models.LoginDTO;
import com.taskmanager.project.models.User;
import com.taskmanager.project.services.AuthService;
import com.taskmanager.project.utils.JwtUtil;
import com.taskmanager.project.utils.PasswordUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private JwtUtil jwt;

    @Operation(description = "Authorize user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authorized",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Bad credentials",
                    content = @Content(mediaType = "application/json"))
    }
    )
    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO data) {
        String email = data.getEmail();
        String password = data.getPassword();

        User user = authService.getByEmail(email);

        if (!PasswordUtil.verify(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Bad credentials");
        }

        String token = JwtUtil.generateToken(user.getId().toString());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
