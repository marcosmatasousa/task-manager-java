package com.taskmanager.project.models;

public class SignUpDTO {
    private final String password;
    private final String email;
    private final String username;

    public SignUpDTO(String email, String username, String password) {
        this.password = password;
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }
}
