package com.hostel.hostelmanagementsystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object representing a contact message submitted by a user.
 * <p>
 * This class is used for encapsulating the input from a contact form including
 * the sender's name, email, and message content. It also tracks whether the message
 * has been read.
 */
@Getter
@Setter
@Data
public class ContactDTO {
    private String name;
    private String email;
    private String message;
    private boolean isRead = false;

}
