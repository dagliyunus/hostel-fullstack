package com.hostel.hostelmanagementsystem.dto;

import com.hostel.hostelmanagementsystem.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object representing payment details.
 * <p>
 * This DTO is used to transfer payment-related data between the backend layers.
 * It includes the payment ID, associated booking ID, payment type, date, and amount.
 */
@Getter
@Setter
public class PaymentDTO {

    private String paymentId;
    private Integer bookingId;
    private PaymentType paymentType;
    private LocalDateTime paymentDate;
    private BigDecimal amount;

    // Constructors
    public PaymentDTO() {
    }

    public PaymentDTO(String paymentId, Integer bookingId, PaymentType paymentType,
                      LocalDateTime paymentDate, BigDecimal amount) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

}