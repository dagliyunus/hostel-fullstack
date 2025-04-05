package com.hostel.hostelmanagementsystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for administrator login credentials.
 *
 * <p>This class is used to encapsulate login data (username and password)
 * submitted by an administrator during authentication. It is typically
 * used in the login API endpoint to transfer login credentials from
 * client to server.
 *
 * <p>The class includes:
 * <ul>
 *   <li><b>username</b>: The administrator's login username.</li>
 *   <li><b>password</b>: The administrator's login password.</li>
 * </ul>
 *
 * <p>Annotations:
 * <ul>
 *   <li>{@code @Data} - Lombok annotation to generate boilerplate code like getters, setters, toString, equals, and hashCode.</li>
 *   <li>{@code @Getter}, {@code @Setter} - Explicitly declared for clarity, though already included in {@code @Data}.</li>
 * </ul>
 */
@Data
@Getter
@Setter
public class AdminLoginDTO {
    private String username;
    private String password;
}