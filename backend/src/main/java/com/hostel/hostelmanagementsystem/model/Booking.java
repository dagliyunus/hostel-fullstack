package com.hostel.hostelmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hostel.hostelmanagementsystem.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Booking")
public class Booking {

    /**
     * Unique identifier for each booking.
     * Maps to the 'booking_id' column in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    /**
     * Reference to the customer who made the booking.
     * Many bookings can be associated with one customer.
     * Mapped using foreign key 'customer_id'.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    /**
     * Reference to the booked bed.
     * Many bookings can be associated with one bed.
     * Mapped using foreign key 'bed_id'.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bed_id", nullable = false)
    private Bed bed;

    /**
     * Date when the booking starts.
     * Cannot be null.
     */
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    /**
     * Date when the booking ends.
     * Cannot be null.
     */
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    /**
     * Status of the booking (e.g., BOOKED, CANCELLED).
     * Enum stored as a string in the database.
     * Cannot be null.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus bookingStatus;

    /**
     * Timestamp when the booking was created.
     * Uses database TIMESTAMP definition for automatic tracking.
     */
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

}
