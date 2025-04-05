package com.hostel.hostelmanagementsystem.controller.user;

import com.hostel.hostelmanagementsystem.service.user.UserRoomService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/user/rooms")
public class UserRoomController {

    private final UserRoomService userRoomService;

    public UserRoomController(UserRoomService userRoomService) {
        this.userRoomService = userRoomService;
    }

    /**
     * Handles HTTP GET requests to fetch available rooms for a specified date range and guest count.
     *
     * <p>Request Format:
     * GET /api/user/rooms/available?checkIn=YYYY-MM-DD&amp;checkOut=YYYY-MM-DD&amp;guests=NUMBER</p>
     *
     * <p>Purpose:
     * This method allows users to check room availability based on their desired check-in/check-out dates
     * and the number of guests. The availability logic is handled by the {@code userRoomService}.</p>
     *
     * <p>Flow:
     * 1. Accepts {@code checkIn}, {@code checkOut}, and {@code guests} as request parameters.
     * 2. Delegates the availability search to {@code userRoomService.findAvailableRooms()}.
     * 3. Returns a list of room identifiers or names that are available within the specified parameters.</p>
     *
     * <p>Response:
     * - 200 OK: List of available room identifiers (e.g., room numbers or IDs).
     * - The list may be empty if no rooms are available matching the criteria.</p>
     *
     * @param checkIn  the desired check-in date in ISO format (yyyy-MM-dd)
     * @param checkOut the desired check-out date in ISO format (yyyy-MM-dd)
     * @param guests   the number of guests to accommodate
     * @return {@link ResponseEntity} containing a list of available room identifiers or names
     */
    @GetMapping("/available")
    public ResponseEntity<List<String>> getAvailableRooms(
            @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam("guests") int guests) {

        List<String> availableRooms = userRoomService.findAvailableRooms(checkIn, checkOut, guests);
        return ResponseEntity.ok(availableRooms);
    }
}
