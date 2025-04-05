package com.hostel.hostelmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for creating a new booking.
 *
 * <p>This class is used to encapsulate all necessary details required when a user or admin
 * initiates the creation of a booking. It includes customer personal information, room
 * details, booking dates, and the total price.
 */
@Getter
@Setter
public class CreateBookingDTO {
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
