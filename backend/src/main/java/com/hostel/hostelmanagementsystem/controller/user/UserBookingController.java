package com.hostel.hostelmanagementsystem.controller.user;

import com.hostel.hostelmanagementsystem.dto.user.CreateUserBookingDTO;
import com.hostel.hostelmanagementsystem.dto.user.UserBookingDTO;
import com.hostel.hostelmanagementsystem.service.user.UserBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/user/bookings")
public class UserBookingController {

    private final UserBookingService userBookingService;

    @Autowired
    public UserBookingController(UserBookingService userBookingService) {
        this.userBookingService = userBookingService;
    }

    /**
     * Handles HTTP POST requests to create a new user booking.
     *
     * <p>This endpoint accepts a {@link CreateUserBookingDTO} request body and attempts to create a new booking
     * using the {@code userBookingService}. If the booking is created successfully, it returns the created
     * {@link UserBookingDTO} object with HTTP 200 (OK) status. If an invalid input or state is encountered,
     * it returns an HTTP 400 (Bad Request) with the error message. Any unexpected exception results in a
     * {@link RuntimeException} being thrown.</p>
     *
     * @param dto the booking data provided by the client to create a new booking
     * @return a {@link ResponseEntity} containing the created booking or an error message
     * @throws RuntimeException if an unexpected error occurs during booking creation
     */
    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(@RequestBody CreateUserBookingDTO dto) {
        try {
            UserBookingDTO created = userBookingService.createBooking(dto);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}