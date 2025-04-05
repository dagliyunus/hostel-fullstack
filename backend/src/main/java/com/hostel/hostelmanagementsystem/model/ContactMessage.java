package com.hostel.hostelmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact_messages")
public class ContactMessage {

    /**
     * Unique identifier for the contact message.
     * Automatically generated using identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the sender. Cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Email address of the sender. Cannot be null.
     */
    @Column(nullable = false)
    private String email;

    /**
     * Content of the message sent by the user.
     * Stored as TEXT in the database. Cannot be null.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    /**
     * Flag indicating whether the message has been read by the admin.
     * Defaults to false.
     */
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    /**
     * Timestamp indicating when the message was sent.
     * Automatically set to current date-time and cannot be updated.
     */
    @Column(name = "sent_at", updatable = false)
    private LocalDateTime sentAt = LocalDateTime.now();
}
