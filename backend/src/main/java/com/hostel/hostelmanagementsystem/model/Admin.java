package com.hostel.hostelmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Admin")
public class Admin {

    /**
     * Primary key for the Admin table.
     * Auto-incremented by the database using IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    /**
     * Admin's username. Required for login and must not be null.
     */
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    /**
     * Admin's unique email. Required and must be unique.
     */
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Hashed version of the admin's password.
     * Stored securely in the database.
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * Timestamp indicating when the admin account was created.
     * Automatically initialized to the current time.
     */
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();
}