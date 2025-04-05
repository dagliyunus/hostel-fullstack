package com.hostel.hostelmanagementsystem.repository;

import com.hostel.hostelmanagementsystem.enums.PaymentType;
import com.hostel.hostelmanagementsystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    /**
     * Retrieves a payment record by its unique payment ID.
     * <p>
     * This method returns an Optional containing the Payment entity if found,
     * or an empty Optional if no payment exists with the given ID.
     *
     * @param paymentId the unique identifier of the payment
     * @return an Optional containing the Payment if found, or empty if not
     */
    Optional<Payment> findByPaymentId(String paymentId);

    /**
     * Retrieves a payment associated with a specific booking ID.
     * <p>
     * Purpose:
     * This method is used to locate a payment record that corresponds to a given booking.
     * <p>
     * Flow:
     * - Accepts a booking ID as input.
     * - Returns the corresponding Payment object if it exists.
     *
     * @param bookingId the ID of the booking associated with the payment
     * @return an Optional containing the matching Payment or empty if none found
     */
    Optional<Payment> findByBooking_BookingId(Integer bookingId);

    /**
     * Retrieves all payment records sorted in descending order by payment date.
     * <p>
     * Purpose:
     * Useful for displaying recent transactions first in admin dashboards or audit logs.
     *
     * @return a list of all Payment entities sorted by payment date in descending order
     */
    List<Payment> findAllByOrderByPaymentDateDesc();

    /**
     * Retrieves all payment records that match the given payment type.
     * <p>
     * Purpose:
     * Allows filtering of payment data by type (e.g., CASH, CARD, ONLINE).
     *
     * @param paymentType the type of payment to filter by
     * @return a list of Payment entities that match the specified type
     */
    List<Payment> findByPaymentType(PaymentType paymentType);

    /**
     * Retrieves all payment records that fall within a specific date-time range.
     * <p>
     * Purpose:
     * Useful for generating financial reports or filtering transactions over a period.
     *
     * @param start the start date-time of the range
     * @param end the end date-time of the range
     * @return a list of Payment entities with payment dates between the specified range
     */
    List<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);

}
