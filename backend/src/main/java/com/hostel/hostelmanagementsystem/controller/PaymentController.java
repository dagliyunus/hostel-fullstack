package com.hostel.hostelmanagementsystem.controller;

import com.hostel.hostelmanagementsystem.dto.PaymentDTO;
import com.hostel.hostelmanagementsystem.enums.PaymentType;
import com.hostel.hostelmanagementsystem.model.Payment;
import com.hostel.hostelmanagementsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for handling payment-related endpoints.
 * <p>
 * This controller is part of the admin-facing API and provides access to payment records.
 * It is mapped under the base URI path "/api/payments".
 * <p>
 * Purpose:
 * Offers endpoints for retrieving payment data, mainly used for admin dashboard or reporting tools.
 * <p>
 * Dependency:
 * Relies on the PaymentService class for business logic and database interaction.
 * Spring automatically injects the PaymentService using constructor injection (@Autowired).
 * <p>
 * Lifecycle:
 * - Initialized at runtime by Spring's component scanning.
 * - Handles HTTP GET requests for retrieving payment data.
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/dashboard/managePayment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Retrieves a payment by its unique payment ID.
     * <p>
     * Purpose:
     * This method allows an admin to fetch payment details using a unique payment ID
     * provided as a query parameter in the request.
     * <p>
     * Request Format(Postman):
     * GET /api/payments/byId?paymentId=PAYMENT_ID
     * <p>
     * Flow:
     * 1. Receives the paymentId from the request parameter.
     * 2. Calls paymentService.findByPaymentId(paymentId) to query the database.
     * 3. If a matching payment is found, returns it with HTTP 200 OK.
     * 4. If not found, responds with HTTP 404 Not Found and an error message.
     * <p>
     * Response:
     * - 200 OK: Returns the payment data.
     * - 404 Not Found: Indicates no payment found with the provided ID.
     *
     * @param paymentId the unique ID of the payment to retrieve
     * @return ResponseEntity containing the payment or an error message
     */
    @GetMapping("/byId")
    public ResponseEntity<?> findById(@RequestParam("paymentId") String paymentId) {
        Optional<Payment> payment = paymentService.findByPaymentId(paymentId);
        if (payment.isPresent()) {
            return ResponseEntity.ok(payment.get());
        }else {
            return ResponseEntity
                    .status(404)
                    .body("Payment not found");
        }
    }

    /**
     * Retrieves a payment associated with a specific booking ID.
     * <p>
     * Request Format:
     * GET /api/admin/dashboard/managePayment/byBooking?bookingId=BOOKING_ID
     * <p>
     * Purpose:
     * Allows admins to fetch a payment record linked to a booking using the booking ID.
     * <p>
     * Flow:
     * - Receives bookingId as a query parameter.
     * - Delegates retrieval logic to paymentService.getPaymentByBookingId().
     * - If a payment is found, returns it with HTTP 200 OK.
     * - If not found, returns HTTP 404 with an appropriate error message.
     *
     * @param bookingId the booking ID used to locate the associated payment
     * @return ResponseEntity with the payment DTO or a not found error message
     */
    @GetMapping("/byBooking")
    public ResponseEntity<?> getPaymentByBookingId(@RequestParam("bookingId") Integer bookingId) {
        try {
            PaymentDTO paymentDTO = paymentService.getPaymentByBookingId(bookingId);
            return ResponseEntity.ok(paymentDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(e.getMessage());
        }
    }

    /**
     * Retrieves all payments that match a specific payment type.
     * <p>
     * Request Format:
     * GET /api/admin/dashboard/managePayment/byType?paymentType=CASH|CARD|TRANSFER
     * <p>
     * Purpose:
     * Allows filtering of payments by type for reporting and categorization.
     * <p>
     * Flow:
     * - Accepts a paymentType as a query parameter.
     * - Calls paymentService.getPaymentsByType() to fetch matching payments.
     * - Returns HTTP 200 OK with the result, or HTTP 400 if the type is invalid.
     *
     * @param paymentType the enum value specifying the type of payment
     * @return ResponseEntity with a list of payments or an error message
     */
    @GetMapping("/byType")
    public ResponseEntity<?> getPaymentsByType(@RequestParam("paymentType") PaymentType paymentType) {
        try {
            List<PaymentDTO> payments = paymentService.getPaymentsByType(paymentType);
            return ResponseEntity.ok(payments);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body("Invalid payment type: " + paymentType);
        }
    }

    /**
     * Retrieves all payments made between the specified date and time range.
     * <p>
     * Request Format:
     * GET /api/admin/dashboard/managePayment/betweenDates?start=START_DATE&end=END_DATE
     * <p>
     * Purpose:
     * Supports temporal filtering of payments, useful for generating reports by time period.
     * <p>
     * Flow:
     * - Accepts ISO_DATE_TIME formatted 'start' and 'end' parameters.
     * - Delegates the query to paymentService.getPaymentsBetweenDates().
     * - Returns the filtered list with HTTP 200 OK.
     *
     * @param start the start date/time of the range
     * @param end the end date/time of the range
     * @return ResponseEntity with a list of PaymentDTOs within the specified range
     */
    @GetMapping("/betweenDates")
    public ResponseEntity<List<PaymentDTO>> getPaymentsBetweenDates(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<PaymentDTO> payments = paymentService.getPaymentsBetweenDates(start, end);
        return ResponseEntity.ok(payments);
    }

    /**
     * Retrieves all payments sorted by payment date in descending order.
     * <p>
     * Request Format:
     * GET /api/admin/dashboard/managePayment/allSorted
     * <p>
     * Purpose:
     * Provides a chronological view of payment history, starting from the most recent.
     * <p>
     * Flow:
     * - Calls paymentService.getAllPaymentsSortedByDateDesc().
     * - Returns a sorted list of PaymentDTOs with HTTP 200 OK.
     *
     * @return ResponseEntity with a list of all payments sorted by date descending
     */
    @GetMapping("/allSorted")
    public ResponseEntity<List<PaymentDTO>> getAllPaymentsSortedByDateDesc() {
        List<PaymentDTO> payments = paymentService.getAllPaymentsSortedByDateDesc();
        return ResponseEntity.ok(payments);
    }
}
