package com.hostel.hostelmanagementsystem.controller.ScheduledJob;

import com.hostel.hostelmanagementsystem.dto.BookingDTO;
import org.springframework.stereotype.Component;

/**
 * A Spring-managed component used for caching the latest booking.
 * <p>
 * This class holds a single instance of {@link BookingDTO} to be accessed and updated
 * in a thread-safe manner using synchronized methods. It is primarily used to store
 * and retrieve the most recent booking in memory, reducing the need to repeatedly query
 * the database for the latest booking data.
 * </p>
 */
@Component
public class BookingCache {

    private BookingDTO latestBooking;

    /**
     * Retrieves the most recently cached booking.
     *
     * @return the latest {@link BookingDTO} instance stored in the cache, or {@code null} if not set
     */
    public synchronized BookingDTO getLatestBooking() {
        return latestBooking;
    }

    /**
     * Updates the cache with the latest booking information.
     *
     * @param latestBooking the new {@link BookingDTO} to store in the cache
     */
    public synchronized void setLatestBooking(BookingDTO latestBooking) {
        this.latestBooking = latestBooking;
    }
}
