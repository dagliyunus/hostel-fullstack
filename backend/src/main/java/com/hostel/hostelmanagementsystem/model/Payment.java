package com.hostel.hostelmanagementsystem.model;

import com.hostel.hostelmanagementsystem.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Payment")
public class Payment {

   /**
    * Primary key for the Payment entity.
    * Uniquely identifies each payment in the database.
    */
   @Id
   @Column(name = "payment_id", nullable = false, length = 50)
   private String paymentId;

    /**
     * Association to the Booking entity.
     * Represents the booking for which the payment is made.
     * This is a many-to-one relationship, as multiple payments may belong to one booking.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    /**
     * Type of payment (e.g., CreditCard, Cash, Paypal).
     * Stored as a string in the database using the ENUM strategy.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type",nullable = false)
    private PaymentType paymentType;

    /**
     * Timestamp when the payment was made.
     * Cannot be null, indicates the exact date and time of the transaction.
     */
    @Column(name = "payment_date",nullable = false)
    private LocalDateTime paymentDate;

    /**
     * Amount paid for the booking.
     * Stored as a decimal value, cannot be null.
     */
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

}
