package com.hostel.hostelmanagementsystem.enums;

/**
 * Enum representing the possible statuses of a booking in the system.
 * <p>
 * This enum is used to define the lifecycle state of a Booking entity.
 * It is mapped to the 'status' column in the database using @Enumerated(EnumType.STRING).
 * <p>
 * Values:
 * - Booked: Indicates that the booking has been successfully created and is currently active.
 * - Canceled: Represents a booking that has been canceled by the customer or admin.
 * - Completed: Indicates that the booking period has ended and the stay is completed.
 */
public enum BookingStatus {
    Booked,
    Cancelled,
    Completed
}
