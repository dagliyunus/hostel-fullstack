package com.hostel.hostelmanagementsystem.repository;

import com.hostel.hostelmanagementsystem.enums.BookingStatus;
import com.hostel.hostelmanagementsystem.model.Bed;
import com.hostel.hostelmanagementsystem.model.Booking;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    /**
     * Retrieves all booking records from the database.
     * <p>
     * Purpose:
     * This method is used to fetch a complete list of all bookings.
     * Commonly utilized in administrative dashboards and analytics.
     * <p>
     * Flow:
     * - Queries the database for all records in the Booking table.
     * - Returns a list of Booking entities.
     *
     * @return a list of all {@link Booking} entities
     */
    @Override
    @NonNull
    List<Booking> findAll();

    /**
     * Retrieves bookings based on their current booking status.
     * <p>
     * Purpose:
     * This method enables filtering of bookings by status such as CONFIRMED, CANCELLED, etc.
     * Useful for monitoring booking lifecycle stages.
     * <p>
     * Flow:
     * - Accepts a {@link BookingStatus} value.
     * - Returns a list of bookings that match the provided status.
     *
     * @param status the booking status to filter by
     * @return list of bookings with the specified status
     */
    List<Booking> findByBookingStatus(BookingStatus status);

    /**
     * Retrieves bookings with check-in dates between the specified start and end dates.
     * <p>
     * Purpose:
     * This method allows querying for bookings within a date range.
     * Useful for generating reports or checking availability.
     * <p>
     * Flow:
     * - Accepts start and end dates as parameters.
     * - Returns a list of bookings with check-in dates within the range.
     *
     * @param startDate the beginning of the check-in date range
     * @param endDate the end of the check-in date range
     * @return list of bookings within the specified check-in date range
     */
    List<Booking> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Retrieves bookings based on customer's first and last name.
     * <p>
     * Purpose:
     * Allows administrators to locate bookings tied to a specific customer.
     * <p>
     * Flow:
     * - Accepts first name and last name as parameters.
     * - Returns a list of bookings associated with the customer.
     *
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     * @return list of bookings associated with the specified customer name
     */
    List<Booking> findByCustomer_FirstNameAndCustomer_LastName(String firstName, String lastName);

    /**
     * Retrieves detailed booking information including related room, bed, and customer entities.
     * <p>
     * Purpose:
     * This method performs a fetch join to retrieve a booking along with its associated room, bed,
     * and customer entities in a single query. It is useful for reducing the number of queries and
     * avoiding lazy loading issues when detailed booking information is needed.
     * <p>
     * Flow:
     * - Accepts a booking ID as a parameter.
     * - Executes a JPQL query using fetch joins on room, bed, and customer associations.
     * - Returns an Optional containing the fully populated Booking entity, or empty if not found.
     *
     * @param bookingId the ID of the booking to retrieve
     * @return an Optional containing the Booking entity with related entities loaded
     */
    @Query("SELECT b FROM Booking b " +
            "JOIN FETCH b.room r " +
            "JOIN FETCH b.bed bed " +
            "JOIN FETCH b.customer c " +
            "WHERE b.bookingId = :bookingId")
    Optional<Booking> findBookingDetailsById(@Param("bookingId") String bookingId);

    /**
     * Counts bookings grouped by their booking status.
     * <p>
     * Purpose:
     * Provides an aggregated count of bookings per status, useful for dashboards and statistics.
     * <p>
     * Flow:
     * - Executes a JPQL query that groups bookings by status and counts them.
     * - Returns a list of object arrays where each array contains the booking status and its count.
     *
     * @return a list of object arrays with each element containing a booking status and its count
     */
    @Query("SELECT b.bookingStatus, COUNT(b) FROM Booking b GROUP BY b.bookingStatus")
    List<Object[]> countBookingsByStatus();

    /**
     * Retrieves the most recently created booking.
     * <p>
     * Purpose:
     * Used to fetch the latest booking based on creation timestamp.
     * <p>
     * Flow:
     * - Orders bookings in descending order by createdAt.
     * - Returns the topmost booking wrapped in an Optional.
     *
     * @return an Optional containing the most recently created Booking
     */
    Optional<Booking> findTopByOrderByCreatedAtDesc();

    /**
     * Deletes a booking by its ID.
     * <p>
     * Purpose:
     * Allows deletion of a booking identified by its primary key.
     * <p>
     * Flow:
     * - Accepts a booking ID.
     * - Deletes the booking record associated with the ID.
     *
     * @param bookingId the ID of the booking to delete
     */
    void deleteById(Integer bookingId);

    /**
     * Retrieves all bookings associated with a specific bed.
     * <p>
     * Purpose:
     * Enables retrieval of bookings made for a particular bed.
     * Useful for checking occupancy and allocation.
     * <p>
     * Flow:
     * - Accepts a Bed entity as a parameter.
     * - Returns a list of bookings related to that bed.
     *
     * @param bed the Bed entity to filter bookings by
     * @return list of bookings associated with the specified bed
     */
    List<Booking> findByBed(Bed bed);

}
