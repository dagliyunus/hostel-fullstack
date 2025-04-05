package com.hostel.hostelmanagementsystem.enums;

/**
 * Enum representing the available types of payment methods.
 * <p>
 * This enum is used in the Payment entity to enforce type safety
 * for the 'payment_type' column in the database. The values are stored
 * as strings using @Enumerated(EnumType.STRING).
 * <p>
 * Values:
 * - CREDIT_CARD: Represents a credit card payment
 * - CASH: Represents a cash payment
 * - PAYPAL: Represents a PayPal payment
 */
public enum PaymentType {
    CREDIT_CARD,
    CASH,
    PAYPAL
}