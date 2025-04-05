package com.hostel.hostelmanagementsystem.service;

import com.hostel.hostelmanagementsystem.controller.ScheduledJob.BookingCache;
import com.hostel.hostelmanagementsystem.dto.BookingDTO;
import com.hostel.hostelmanagementsystem.service.admin.BookingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduledJobService {
    private final BookingService bookingService;
    private final BookingCache bookingCache;

    public ScheduledJobService(BookingService bookingService, BookingCache bookingCache, NotificationService notificationService) {
        this.bookingService = bookingService;
        this.bookingCache = bookingCache;
    }

    /**
     * Scheduled task that periodically polls for the latest booking and updates the cache if a new booking is detected.
     * <p>
     * This method is executed at a fixed rate of every 10 seconds.
     * It retrieves the most recent booking from the database and compares it to the currently cached booking.
     * If the new booking has a higher ID (i.e., is more recent), the cache is updated and a message is printed to the console.
     * <p>
     * Purpose:
     * This mechanism allows real-time awareness of new bookings by maintaining the latest booking in memory.
     * This can be useful for triggering notifications, updates in the admin panel, or alert systems.
     */
    @Scheduled(fixedRate = 10000) // 2 minutes
    public void pollLatestBooking() {
        Optional<BookingDTO> newest = bookingService.getLatestBooking();
        newest.ifPresent(newBooking -> {
            BookingDTO cached = bookingCache.getLatestBooking();

            if (cached == null || newBooking.getBookingId() > cached.getBookingId()) {
                bookingCache.setLatestBooking(newBooking);
                System.out.println("New booking detected: " + newBooking.getCustomerFullName());
            }
        });
    }
}