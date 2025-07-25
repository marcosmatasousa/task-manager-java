package com.taskmanager.project.models;
import com.taskmanager.project.utils.PasswordUtil;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    @NotBlank
    public String username;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    public User() {}
    public User(Long id, String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
