package com.hostel.hostelmanagementsystem.dto;

import com.hostel.hostelmanagementsystem.model.Booking;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a Booking entity.
 *
 * <p>This class is used to transfer booking data between the frontend and backend layers
 * without exposing the entity directly. It includes customer details, room and bed identifiers,
 * booking status, dates, and payment-related information.</p>
 */
@Getter
@Setter
public class BookingDTO {
    private Integer bookingId;
    private String customerFullName;
    private String customerEmail;
    private String roomNumber;
    private String bedNumber;
    private String bookingStatus;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private BigDecimal totalPrice;

    public BookingDTO() {}
}