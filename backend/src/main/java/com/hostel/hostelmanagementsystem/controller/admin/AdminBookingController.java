package com.hostel.hostelmanagementsystem.controller.admin;


import com.hostel.hostelmanagementsystem.dto.BookingDTO;
import com.hostel.hostelmanagementsystem.dto.CreateBookingDTO;
import com.hostel.hostelmanagementsystem.service.admin.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/dashboard/manageBooking")
public class AdminBookingController {

    private final BookingService bookingService;

    @Autowired
    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Retrieves all bookings in the system.
     * <p>
     * Purpose:
     * This endpoint is designed for admin users to access a complete list of bookings.
     * It is useful for rendering booking lists in admin dashboard under "Manage Booking" tab.
     * <p>
     * Request Format (Postman):
     * GET /api/bookings/getAllBookings
     * <p>
     * How it works:
     * 1. Calls the bookingService.getAllBookings() method which fetches all Booking entities from the database.
     * 2. Checks if the list is not empty.
     * 3. If there are bookings, responds with 200 OK and the list.
     * 4. If no bookings exist, returns a 404 Not Found status with an appropriate message.
     * <p>
     * Response:
     * - 200 OK: List of Booking objects.
     * - 404 Not Found: No bookings present in the system.
     *
     * @return ResponseEntity with a list of bookings or an error message
     */
    @GetMapping("/getAllBookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookingsDTO = bookingService.getAllBookings();
        return ResponseEntity.ok(bookingsDTO); // always return 200
    }

    /**
     * Handles GET requests to find bookings by guest's first and last name.
     * <p>
     * Request Format:
     * GET /api/bookings/findByName?firstName=John&lastName=Doe
     * <p>
     * Purpose:
     * This method allows users to retrieve all bookings associated with a specific guest
     * by providing their first and last name. Useful for filtering bookings by person.
     * <p>
     * Flow:
     * - The method extracts the first and last name from the request parameters.
     * - BookingService checks for bookings matching the given name.
     * - If found, the list of bookings is returned.
     * - If no matching bookings are found, returns 404 Not Found with a null body.
     *
     * @param firstName the guest's first name
     * @param lastName the guest's last name
     * @return ResponseEntity containing the list of BookingDTOs or 404 status
     */
    @GetMapping("/findByName")
    public ResponseEntity<List<BookingDTO>> findByName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        List<BookingDTO> bookings = bookingService.findByName(firstName,lastName);
        if (!bookings.isEmpty()) {
            return ResponseEntity.ok(bookings);
        }else{
            return ResponseEntity
                    .status(404)
                    .body(null);
        }
    }

    /**
     * Handles GET requests to find bookings within a specific check-in date range.
     * <p>
     * Request Format:
     * GET /api/bookings/findByCheckInDateRange?start=2025-04-01&end=2025-04-15
     * <p>
     * Purpose:
     * This method retrieves bookings that fall within a specified check-in date interval.
     * Useful for generating reports or tracking occupancy within time frames.
     * <p>
     * Flow:
     * - The start and end dates are extracted from the request parameters.
     * - BookingService retrieves all bookings between the given dates.
     * - If results exist, they are returned as a list.
     * - If no results are found, returns 404 Not Found with a null body.
     *
     * @param startDate the start date of the check-in range (inclusive)
     * @param endDate the end date of the check-in range (inclusive)
     * @return ResponseEntity containing the list of BookingDTOs or 404 status
     */
    @GetMapping("/findByCheckInDateRange")
    public ResponseEntity<List<BookingDTO>> findByCheckInDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<BookingDTO> bookings = bookingService.findByCheckInDateBetween(startDate, endDate);

        if (!bookings.isEmpty()) {
            return ResponseEntity.ok(bookings);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    /**
     * Handles GET requests to find bookings by their current status.
     * <p>
     * Request Format:
     * GET /api/bookings/findByStatus?status=CONFIRMED
     * <p>
     * Purpose:
     * This method allows clients to filter bookings based on their status.
     * Useful for workflow monitoring, filtering confirmed/cancelled/pending bookings.
     * <p>
     * Flow:
     * - The booking status is extracted from the request parameter.
     * - BookingService searches for bookings matching the status.
     * - If found, the list of bookings is returned.
     * - If not found or invalid status, appropriate 404 response is returned.
     *
     * @param status the status to filter bookings by (e.g., CONFIRMED, CANCELLED)
     * @return ResponseEntity with list of BookingDTOs, or 404 with message
     */
    @GetMapping("/findByStatus")
    public ResponseEntity<?> findByBookingStatus(@RequestParam("status") String status) {
        try {
            List<BookingDTO> bookings = bookingService.findByBookingStatus(status);

            if (!bookings.isEmpty()) {
                return ResponseEntity.ok(bookings);
            } else {
                return ResponseEntity.status(404)
                        .body("No bookings found with status: " + status);
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404)
                    .body("Invalid booking status: " + status);
        }
    }

    /**
     * Handles GET requests to retrieve booking details by booking ID.
     * <p>
     * Request Format:
     * GET /api/bookings/getDetails?bookingId=B6001
     * <p>
     * Purpose:
     * This method retrieves detailed information about a specific booking using its ID.
     * Useful for accessing full booking data such as user info, property, status, and dates.
     * <p>
     * Flow:
     * - The booking ID is extracted from the request parameter.
     * - BookingService attempts to retrieve booking details using the ID.
     * - If found, the booking details are returned.
     * - If not found, a 404 response is returned with an error message.
     *
     * @param bookingId the ID of the booking to retrieve
     * @return ResponseEntity with BookingDTO or 404 with error message
     */
    @GetMapping("/getDetails")
    public ResponseEntity<?> getBookingDetails(@RequestParam("bookingId") String bookingId) {
        try{
            BookingDTO booking = bookingService.getBookingDetailsById(bookingId);
            return ResponseEntity.ok(booking);
        }catch (NoSuchElementException e){
            return ResponseEntity
                    .status(404)
                    .body("No booking found with id: " + bookingId);
        }
    }

    /**
     * Handles PUT requests to cancel a booking based on its ID.
     * <p>
     * Request Format:
     * PUT /api/bookings/cancelBooking?bookingId=6001
     * <p>
     * Purpose:
     * This method allows cancellation of a booking if it exists and is eligible.
     * Useful for customer cancellation logic and admin operations.
     * <p>
     * Flow:
     * - The booking ID is extracted from the request parameter.
     * - BookingService attempts to cancel the booking.
     * - If successful, an OK response is returned.
     * - If the booking does not exist, a 404 response is returned with an error message.
     *
     * @param bookingId the ID of the booking to cancel
     * @return ResponseEntity indicating success or 404 with error message
     */
    @PutMapping("/cancelBooking")
    public ResponseEntity<?> cancelBooking(@RequestParam("bookingId") Integer bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok().build();
        }catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(404)
                    .body("No booking found with id: " + bookingId);
        }
    }

    /**
     * Handles GET requests to retrieve counts of bookings by status.
     * <p>
     * Request Format:
     * GET /api/bookings/statusCounts
     * <p>
     * Purpose:
     * This method provides a summary count of bookings grouped by their statuses.
     * Useful for dashboards, analytics, and operational monitoring.
     * <p>
     * Flow:
     * - BookingService aggregates booking counts grouped by their current status.
     * - The result is returned as a map of status labels to counts.
     *
     * @return ResponseEntity with a map of booking statuses and their respective counts
     */
    @GetMapping("/statusCounts")
    public ResponseEntity<Map<String, Long>> getBookingStatusCounts() {
        Map<String, Long> statusCounts = bookingService.getBookingStatusCounts();
        return ResponseEntity.ok(statusCounts);
    }

    /**
     * Handles POST requests to create a new booking.
     * <p>
     * Request Format:
     * POST /api/bookings/createBooking
     * <p>
     * Purpose:
     * This method creates a new booking based on the provided booking data.
     * It validates the input and delegates creation logic to the BookingService.
     * <p>
     * Flow:
     * - Receives a CreateBookingDTO via request body.
     * - BookingService processes and persists the booking.
     * - Returns the created booking if successful.
     * - Returns 400 Bad Request if input is invalid.
     * - Returns 500 Internal Server Error for unexpected failures.
     *
     * @param dto the booking data to be created
     * @return ResponseEntity with the created booking or error message
     */
    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(@RequestBody CreateBookingDTO dto) {
        try {
            BookingDTO createdBooking = bookingService.createBooking(dto);
            return ResponseEntity.ok(createdBooking);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // log full stacktrace to console
            return ResponseEntity.status(500) // <-- use real 500
                    .body("Booking could not be created: " + e.getMessage());
        }
    }

    /**
     * Handles GET requests to retrieve the latest booking.
     * <p>
     * Request Format:
     * GET /api/bookings/latest
     * <p>
     * Purpose:
     * This method fetches the most recently created booking.
     * Useful for confirming recent operations or displaying current data.
     * <p>
     * Flow:
     * - BookingService retrieves the latest booking entry.
     * - If found, it is returned in the response.
     * - If no booking exists, returns HTTP 204 No Content.
     *
     * @return ResponseEntity containing the latest BookingDTO or no content
     */
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestBooking() {
        Optional<BookingDTO> latest = bookingService.getLatestBooking();
        return latest.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    /**
     * Handles DELETE requests to remove a booking by its ID.
     * <p>
     * Request Format:
     * DELETE /api/bookings/deleteBooking/{bookingId}
     * <p>
     * Purpose:
     * This method deletes the booking identified by the given ID.
     * <p>
     * Flow:
     * - Booking ID is extracted from the path variable.
     * - BookingService performs the deletion.
     * - Returns success message upon deletion.
     * - Returns HTTP 500 with error message if deletion fails.
     *
     * @param bookingId the ID of the booking to delete
     * @return ResponseEntity with success or failure message
     */
    @DeleteMapping("deleteBooking/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Integer bookingId) {
        try {
            bookingService.deleteBookingById(bookingId);
            return ResponseEntity.ok("Booking deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete booking.");
        }
    }
}
