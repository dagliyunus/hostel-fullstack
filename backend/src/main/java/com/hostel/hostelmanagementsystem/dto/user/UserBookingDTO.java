package com.hostel.hostelmanagementsystem.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object representing a user booking.
 * <p>
 * This DTO is used to encapsulate booking information for the user-facing API responses,
 * typically returned after creating a booking or retrieving existing bookings.
 * </p>
 */
@Getter
@Setter
public class UserBookingDTO {
    private Integer bookingId;
    private String customerFullName;
    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalPrice;
    private String paymentId;
}