package com.hostel.hostelmanagementsystem.service.user;

import com.hostel.hostelmanagementsystem.model.Bed;
import com.hostel.hostelmanagementsystem.model.Booking;
import com.hostel.hostelmanagementsystem.model.Room;
import com.hostel.hostelmanagementsystem.repository.BedRepository;
import com.hostel.hostelmanagementsystem.repository.BookingRepository;
import com.hostel.hostelmanagementsystem.repository.RoomRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoomService {

    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final BookingRepository bookingRepository;

    public UserRoomService(RoomRepository roomRepository,
                           BedRepository bedRepository,
                           BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Finds a list of room numbers that have enough available beds for the specified number of guests
     * within the given check-in and check-out date range.
     *
     * <p>This method checks all rooms and their beds. It uses the helper method {@code isBedAvailable}
     * to determine whether each bed is available for the given period. If a room has at least as many
     * available beds as the number of guests, it is added to the list of available rooms.
     *
     * @param checkIn  the desired check-in date
     * @param checkOut the desired check-out date
     * @param guests   the number of guests requiring beds
     * @return a list of room numbers that satisfy the availability requirement
     */
    public List<String> findAvailableRooms(LocalDate checkIn, LocalDate checkOut, int guests) {
        System.out.println(" Checking availability for:");
        System.out.println(" Check-in: " + checkIn + ", Check-out: " + checkOut + ", Guests: " + guests);

        List<Room> allRooms = roomRepository.findAll();
        List<String> availableRoomNumbers = new ArrayList<>();

        for (Room room : allRooms) {
            List<Bed> beds = bedRepository.findByRoom(room);
            System.out.println(" Room " + room.getRoomNumber() + " has " + beds.size() + " beds");

            long availableBedCount = beds.stream()
                    .filter(bed -> {
                        boolean isAvailable = isBedAvailable(bed, checkIn, checkOut);
                        System.out.println("  → Bed " + bed.getBedNumber() + " is " + (isAvailable ? " available" : " unavailable"));
                        return isAvailable;
                    })
                    .count();

            System.out.println(" Room " + room.getRoomNumber() + " has " + availableBedCount + " available bed(s)");

            if (availableBedCount >= guests) {
                System.out.println(" Room " + room.getRoomNumber() + " added to available rooms");
                availableRoomNumbers.add(room.getRoomNumber());
            } else {
                System.out.println(" Room " + room.getRoomNumber() + " skipped (not enough beds)");
            }
        }

        System.out.println("🏁 Final available rooms: " + availableRoomNumbers);
        return availableRoomNumbers;
    }

    /**
     * Determines if a specific bed is available during the given date range.
     *
     * <p>Checks all existing bookings for the bed to ensure there is no date overlap
     * with the provided check-in and check-out dates.
     *
     * @param bed      the bed to check for availability
     * @param checkIn  the desired check-in date
     * @param checkOut the desired check-out date
     * @return {@code true} if the bed is available, {@code false} if there is a conflict
     */
    private boolean isBedAvailable(Bed bed, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> bookings = bookingRepository.findByBed(bed);

        System.out.println(" Checking bed: " + bed.getBedNumber() + " in room: " + bed.getRoom().getRoomNumber());
        System.out.println(" Target: " + checkIn + " to " + checkOut);
        System.out.println(" Found bookings: " + bookings.size());

        for (Booking booking : bookings) {
            LocalDate existingCheckIn = booking.getCheckInDate();
            LocalDate existingCheckOut = booking.getCheckOutDate();

            System.out.println(" Existing booking: " + existingCheckIn + " to " + existingCheckOut);

            if (!(checkOut.isBefore(existingCheckIn) || checkIn.isAfter(existingCheckOut))) {
                System.out.println(" Conflict with existing booking → BED UNAVAILABLE");
                return false;
            }
        }

        System.out.println(" Bed is available");
        return true;
    }
}
