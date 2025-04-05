package com.hostel.hostelmanagementsystem.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for sending admin login responses.
 *
 * <p>This class encapsulates the response returned to the client after an admin login attempt.
 * It contains a message indicating the login result, an optional authentication token,
 * and the unique identifier of the admin.</p>
 *
 * <p>Lombok annotations {@code @Data}, {@code @Getter}, and {@code @Setter} automatically generate
 * boilerplate code such as getters, setters, equals, hashCode, and toString methods.</p>
 */
@Data
@Getter
@Setter
public class AdminLoginResponseDTO {
    private String message;
    private String token; // optional
    private Integer adminId;

    public AdminLoginResponseDTO(String message, String token, Integer adminId) {
        this.message = message;
        this.token = token;
        this.adminId = adminId;
    }
}