package com.hostel.hostelmanagementsystem.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) used to capture booking information submitted by a user.
 *
 * <p>This DTO contains customer personal details and the desired booking details including
 * check-in/check-out dates and total price. It is typically used in POST requests from the frontend
 * when a new user booking is submitted.
 */
@Getter
@Setter
public class CreateUserBookingDTO {
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private String customerPhone;
    private LocalDate customerDateOfBirth;

    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalPrice;
}