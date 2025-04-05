package com.hostel.hostelmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for exposing detailed customer information
 * in the Admin Dashboard or backend management views.
 * <p>
 * This class aggregates data from multiple entities like Customer, Room, and Bed.
 * It is used to present a simplified, readable format tailored for the admin view,
 * combining relevant information such as:
 * - customer personal details
 * - contact information
 * - room and bed assignments
 * <p>
 * Lombok annotations (@Getter, @Setter) are used to auto-generate getters and setters.
 * DTO is manually mapped from entity classes. No automatic mapping libraries (e.g., MapStruct) are used in this project.
 */
@Setter
@Getter
public class AdminCustomerDTO {

    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private LocalDateTime registeredAt;
    private String roomNumber;
    private String bedNumber;

    // Constructor
    public AdminCustomerDTO(String customerId, String firstName, String lastName, String email, String phone, LocalDate dateOfBirth, String roomNumber, String bedNumber, LocalDateTime registeredAt) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.roomNumber = roomNumber;
        this.registeredAt = registeredAt;
        this.bedNumber = bedNumber;
    }

}
