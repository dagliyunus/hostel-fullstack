package com.hostel.hostelmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for transferring notification data across layers.
 * <p>
 * This class encapsulates the information related to a booking notification, such as the
 * message content, booking details, customer identity, and status of the notification.
 * It is primarily used in the admin dashboard for displaying recent activity.
 */
@Getter
@Setter
public class NotificationDTO {
    private Long id;
    private String title;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private String customerFullName;
    private String roomNumber;
    private String bedNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalPrice;

    public NotificationDTO() {}

}
