package com.hostel.hostelmanagementsystem.service;

import com.hostel.hostelmanagementsystem.dto.PaymentDTO;
import com.hostel.hostelmanagementsystem.enums.PaymentType;
import com.hostel.hostelmanagementsystem.model.Payment;
import com.hostel.hostelmanagementsystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    /**
     * Repository instance to interact with the Payment table in the database.
     * This is automatically injected by Spring using constructor injection.
     */
    private final PaymentRepository paymentRepository;


    /**
     * Constructs a PaymentService with a PaymentRepository dependency.
     * This allows the service layer to communicate with the data access layer.
     *
     * @param paymentRepository the repository used to fetch and manage payment entities
     */
    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Retrieves a Payment entity by its unique payment ID.
     * Delegates the operation to the repository layer using a custom method `findByPaymentId`
     * (not the default `findById` method from JpaRepository).
     *
     * @param paymentId the ID of the payment to retrieve
     * @return an Optional containing the Payment if found, or empty if not
     */
    public Optional<Payment> findByPaymentId(String paymentId) {
        return paymentRepository.findByPaymentId(paymentId);
    }

    /**
     * Retrieves a payment record associated with a specific booking ID.
     * <p>
     * This method queries the database for a {@link Payment} entity that matches the provided booking ID,
     * and maps it to a {@link PaymentDTO}. If no payment is found for the given booking ID, an
     * {@link IllegalArgumentException} is thrown.
     *
     * @param bookingId the ID of the booking for which payment data is to be retrieved
     * @return a {@link PaymentDTO} representing the payment details
     * @throws IllegalArgumentException if no payment is found for the given booking ID
     */
    public PaymentDTO getPaymentByBookingId(Integer bookingId) {
        Payment payment = paymentRepository.findByBooking_BookingId(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("No payment found for booking ID: " + bookingId));

        return mapToDTO(payment);
    }

    /**
     * Retrieves all payments filtered by the specified payment type.
     * <p>
     * This method fetches all {@link Payment} records with the provided {@link PaymentType},
     * and maps them into a list of {@link PaymentDTO}.
     *
     * @param paymentType the type of payment to filter (e.g., CREDIT_CARD, CASH)
     * @return a list of {@link PaymentDTO} matching the specified payment type
     */
    public List<PaymentDTO> getPaymentsByType(PaymentType paymentType) {
        List<Payment> payments = paymentRepository.findByPaymentType(paymentType);

        return payments.stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Retrieves payments made within a specified date and time range.
     * <p>
     * This method filters {@link Payment} records whose paymentDate is between the provided start and end
     * {@link LocalDateTime} values, and maps them into {@link PaymentDTO} objects.
     *
     * @param start the start of the date-time range
     * @param end the end of the date-time range
     * @return a list of {@link PaymentDTO} representing payments in the specified range
     */
    public List<PaymentDTO> getPaymentsBetweenDates(LocalDateTime start, LocalDateTime end) {
        List<Payment> payments = paymentRepository.findByPaymentDateBetween(start, end);

        return payments.stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Retrieves all payments sorted by payment date in descending order.
     * <p>
     * This method returns all {@link Payment} records from the database, ordered by payment date (newest first),
     * and maps them into {@link PaymentDTO} objects.
     *
     * @return a list of {@link PaymentDTO} sorted by payment date in descending order
     */
    public List<PaymentDTO> getAllPaymentsSortedByDateDesc() {
        List<Payment> payments = paymentRepository.findAllByOrderByPaymentDateDesc();
        return payments.stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Generates the next unique payment ID in the format "PY<number>".
     * <p>
     * This method retrieves all existing payments from the database,
     * extracts the numeric part of each payment ID that starts with "PY",
     * and calculates the maximum number found. It then increments this
     * number by one and returns the new payment ID.
     * <p>
     * This approach ensures that each generated payment ID is unique and sequential.
     *
     * @return a new unique payment ID string (e.g., "PY101")
     */
    public String generateNextPaymentId() {
        List<Payment> allPayments = paymentRepository.findAll();

        int max = allPayments.stream()
                .map(Payment::getPaymentId)
                .filter(id -> id.startsWith("PY"))
                .map(id -> id.substring(2))       // remove "PY"
                .filter(s -> s.matches("\\d+"))   // digits only
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return "PY" + (max + 1);
    }

    /**
     * Converts a {@link Payment} entity into a {@link PaymentDTO}.
     * <p>
     * This method extracts relevant data from the Payment entity, such as payment ID,
     * booking ID, payment type, date, and amount, and sets them into a PaymentDTO.
     * <p>
     * It is typically used to prepare data for transfer between layers without exposing
     * the internal structure of the entity.
     *
     * @param payment the {@link Payment} entity to convert
     * @return a {@link PaymentDTO} containing the mapped data
     */
    private PaymentDTO mapToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBooking().getBookingId());
        dto.setPaymentType(payment.getPaymentType());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        return dto;
    }
}
