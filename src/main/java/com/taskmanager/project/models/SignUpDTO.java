package com.taskmanager.project.models;

public class SignUpDTO {
    private final String password;
    private final String email;
    private final String username;

    public SignUpDTO(String password, String email, String username) {
        this.password = password;
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
