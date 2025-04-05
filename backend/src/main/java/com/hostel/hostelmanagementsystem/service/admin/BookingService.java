package com.hostel.hostelmanagementsystem.service.admin;

import com.hostel.hostelmanagementsystem.dto.BookingDTO;
import com.hostel.hostelmanagementsystem.dto.CreateBookingDTO;
import com.hostel.hostelmanagementsystem.enums.BookingStatus;
import com.hostel.hostelmanagementsystem.enums.PaymentType;
import com.hostel.hostelmanagementsystem.model.*;
import com.hostel.hostelmanagementsystem.repository.*;
import com.hostel.hostelmanagementsystem.service.PaymentService;
import com.hostel.hostelmanagementsystem.service.SmsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final NotificationRepository notificationRepository;
    private final PaymentRepository paymentRepository;
    private final CustomerService customerService;
    private final PaymentService paymentService;


    @Autowired
    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, RoomRepository roomRepository, BedRepository bedRepository, NotificationRepository notificationRepository, PaymentRepository paymentRepository, CustomerService customerService, PaymentService paymentService, SmsService smsService) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
        this.notificationRepository = notificationRepository;
        this.paymentRepository = paymentRepository;
        this.customerService = customerService;
        this.paymentService = paymentService;
    }

    /**
     * Retrieves all bookings from the database and converts them into DTOs.
     * <p>
     * Purpose:
     * This method is used to fetch a list of all booking records in the system.
     * It maps each {@link Booking} entity to a {@link BookingDTO} object for easier data handling in the frontend.
     * <p>
     * Flow:
     * 1. Calls the repository to fetch all Booking entities.
     * 2. Maps each Booking to a BookingDTO using stream operations.
     * 3. Returns the list of BookingDTOs.
     *
     * @return a list of BookingDTO representing all bookings in the system
     */
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream()
                .map(b -> {
                    BookingDTO dto = new BookingDTO();
                    dto.setBookingId(b.getBookingId());
                    dto.setCustomerFullName(b.getCustomer().getFirstName() + " " + b.getCustomer().getLastName());
                    dto.setCustomerEmail(b.getCustomer().getEmail());
                    dto.setRoomNumber(b.getRoom().getRoomNumber());
                    dto.setBedNumber(b.getBed().getBedNumber());
                    dto.setBookingStatus(b.getBookingStatus().toString());
                    dto.setCheckInDate(b.getCheckInDate());
                    dto.setCheckOutDate(b.getCheckOutDate());
                    dto.setTotalPrice(b.getTotalPrice());
                    dto.setCreatedAt(b.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Finds all bookings that match the given customer's first and last name.
     * <p>
     * Purpose:
     * This method enables filtering bookings based on customer names for administrative or reporting needs.
     * <p>
     * Flow:
     * 1. Takes the customer's first name and last name as parameters.
     * 2. Queries the booking repository to find matches by full name.
     * 3. Maps the matching Booking entities to BookingDTOs.
     *
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     * @return a list of BookingDTOs matching the provided customer name
     */
    public List<BookingDTO> findByName(String firstName, String lastName){
        List<Booking> bookings = bookingRepository.findByCustomer_FirstNameAndCustomer_LastName(firstName, lastName);
        return bookings
                .stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a list of bookings that fall within the specified check-in date range.
     * <p>
     * Purpose:
     * Allows administrators to filter and analyze bookings based on when customers checked in.
     * Useful for reports and occupancy tracking.
     * <p>
     * Flow:
     * 1. Accepts a start and end date.
     * 2. Queries the repository for bookings where the check-in date falls between the provided range.
     * 3. Maps each booking to a BookingDTO.
     *
     * @param startDate the start of the check-in date range
     * @param endDate   the end of the check-in date range
     * @return list of BookingDTOs within the specified date range
     */
    public List<BookingDTO> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate){
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startDate, endDate);
        return bookings
                .stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves bookings filtered by their booking status.
     * <p>
     * Purpose:
     * This method enables filtering of bookings based on whether they are booked, cancelled, etc.
     * Useful for administrative dashboards or analytics.
     * <p>
     * Flow:
     * 1. Accepts a booking status as a string (must match enum values).
     * 2. Converts the string to a BookingStatus enum.
     * 3. Queries the repository for bookings matching that status.
     * 4. Maps the results to BookingDTOs.
     *
     * @param status the status to filter bookings by (e.g., Booked, Cancelled)
     * @return list of BookingDTOs that match the given status
     * @throws IllegalArgumentException if the status is not valid
     */
    public List<BookingDTO> findByBookingStatus(String status) {
        try {
            BookingStatus bookingStatus = BookingStatus.valueOf(status); // exact match required here

            List<Booking> bookings = bookingRepository.findByBookingStatus(bookingStatus);

            return bookings.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid booking status: " + status);
        }
    }

    /**
     * Retrieves full booking details by booking ID.
     * <p>
     * Purpose:
     * Used to display all related information of a specific booking.
     * Typically used in admin dashboards or detailed view modals.
     * <p>
     * Flow:
     * 1. Accepts a booking ID as a string.
     * 2. Queries the repository for a booking that matches the ID and fetches related entities.
     * 3. Maps the entity to BookingDTO.
     *
     * @param bookingId the unique identifier of the booking
     * @return BookingDTO with full booking information
     * @throws IllegalArgumentException if no booking is found with the given ID
     */
    public BookingDTO getBookingDetailsById(String bookingId) {
        Booking booking = bookingRepository.findBookingDetailsById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        return mapToDTO(booking);
    }

    /**
     * Cancels an existing booking by its ID.
     * <p>
     * Purpose:
     * Allows administrators to mark a booking as cancelled.
     * This can be used for scenarios where a customer has cancelled a reservation
     * or the booking is no longer valid.
     * <p>
     * Flow:
     * 1. Retrieves the booking by its ID.
     * 2. If the booking exists, updates its status to {@link BookingStatus#Cancelled}.
     * 3. Saves the updated booking back to the repository.
     *
     * @param bookingId the ID of the booking to cancel
     * @throws IllegalArgumentException if no booking is found with the given ID
     */
    public void cancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        booking.setBookingStatus(BookingStatus.Cancelled);
        bookingRepository.save(booking);
    }

    /**
     * Retrieves the count of bookings grouped by their booking status.
     * <p>
     * Purpose:
     * Useful for analytics, reporting, and dashboard summary views to display how many bookings
     * fall under each status such as Booked, Cancelled, etc.
     * <p>
     * Flow:
     * 1. Calls a custom repository query that groups bookings by status and counts them.
     * 2. Iterates through the results and populates a map with status name and count.
     *
     * @return a map of booking status names to their respective count
     */
    public Map<String, Long> getBookingStatusCounts() {
        List<Object[]> results = bookingRepository.countBookingsByStatus();

        Map<String, Long> statusCounts = new LinkedHashMap<>();
        for (Object[] row : results) {
            BookingStatus status = (BookingStatus) row[0];
            Long count = (Long) row[1];
            statusCounts.put(status.name(), count);
        }

        return statusCounts;
    }

    /**
     * Creates a new booking along with associated customer, payment, and notification records.
     * <p>
     * Purpose:
     * This method is used to handle a full lifecycle of a new booking from room and bed allocation,
     * to creating a customer, saving booking information, creating a notification, and registering a payment.
     * It ensures that all necessary associations and validations are done atomically using transactional boundaries.
     *
     * <p>
     * Flow:
     * 1. Retrieves the room by room number from the repository.
     * 2. Searches for an available bed in that room that is not already assigned to a customer.
     * 3. Creates and saves a new customer entity with the provided information.
     * 4. Constructs a booking entity with the customer, room, and bed, then saves it.
     * 5. Creates a notification for the new booking and saves it to the repository.
     * 6. Generates a payment record with a default payment type and stores it.
     * 7. Returns a DTO representation of the saved booking.
     *
     * @param dto the DTO containing booking and customer details submitted by the frontend
     * @return the DTO representation of the created booking
     * @throws IllegalArgumentException if the room is not found or no available beds exist
     * @throws IllegalStateException if no available bed is found in the room
     */
    @Transactional
    public BookingDTO createBooking(CreateBookingDTO dto) {
        //  1. Find Room
        Room room = roomRepository.findByRoomNumber(dto.getRoomNumber())
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + dto.getRoomNumber()));

        //  2. Find available Bed
        List<Bed> beds = bedRepository.findByRoom(room);
        Bed availableBed = beds.stream()
                .filter(bed -> !customerRepository.existsByBed(bed))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No available beds in room: " + dto.getRoomNumber()));

        //  3. Create Customer
        Customer customer = new Customer();
        customer.setFirstName(dto.getCustomerFirstName());
        customer.setLastName(dto.getCustomerLastName());
        customer.setEmail(dto.getCustomerEmail());
        customer.setPhone(dto.getCustomerPhone());
        customer.setDateOfBirth(dto.getCustomerDateOfBirth());
        customer.setRegisteredAt(LocalDateTime.now());
        customer.setRoom(room);
        customer.setBed(availableBed);
        customer.setCustomerId(customerService.generateNextCustomerId());

        customerRepository.save(customer);

        //  4. Create Booking
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setBed(availableBed);
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setBookingStatus(BookingStatus.Booked);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setTotalPrice(dto.getTotalPrice());

        Booking savedBooking = bookingRepository.save(booking);

        //  5. Create Notification
        Notification notification = new Notification();
        notification.setCustomerFullName(customer.getFirstName() + " " + customer.getLastName());
        notification.setBookingId(savedBooking.getBookingId());
        notification.setRoomNumber(room.getRoomNumber());
        notification.setBedNumber(availableBed.getBedNumber());
        notification.setCheckInDate(savedBooking.getCheckInDate());
        notification.setCheckOutDate(savedBooking.getCheckOutDate());
        notification.setTitle("New Booking");
        notification.setMessage("Booking created successfully for " + customer.getFirstName() + " " + customer.getLastName());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setTotalPrice(savedBooking.getTotalPrice());

        notificationRepository.save(notification);

        String nextPaymentId = paymentService.generateNextPaymentId();

        Payment payment = new Payment();
        payment.setPaymentId(nextPaymentId);
        payment.setBooking(savedBooking);
        payment.setPaymentType(PaymentType.CREDIT_CARD); // default for now
        payment.setAmount(dto.getTotalPrice());
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);


        return mapToDTO(savedBooking);
    }

    /**
     * Retrieves the most recently created booking in the system.
     * <p>
     * Purpose:
     * Useful for displaying the latest booking on dashboards or summary views.
     * <p>
     * Flow:
     * 1. Queries the booking repository for the most recent booking sorted by creation timestamp.
     * 2. Maps the booking entity to a BookingDTO.
     *
     * @return an Optional containing the most recently created BookingDTO, or empty if no bookings exist
     */
    public Optional<BookingDTO> getLatestBooking() {
        return bookingRepository.findTopByOrderByCreatedAtDesc()
                .map(this::mapToDTO);
    }

    /**
     * Deletes a booking from the database using its unique booking ID.
     * <p>
     * Purpose:
     * This method allows administrators to remove a booking record.
     * Typically used in admin dashboard operations or booking management tools.
     * <p>
     * Flow:
     * 1. Accepts a booking ID as input.
     * 2. Calls the repository to delete the corresponding booking.
     *
     * @param bookingId the unique identifier of the booking to delete
     */
    public void deleteBookingById(Integer bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    /**
     * Converts a Booking entity into a BookingDTO.
     * <p>
     * Purpose:
     * Facilitates the transformation of Booking objects into DTOs for frontend consumption.
     * <p>
     * Flow:
     * 1. Accepts a Booking entity.
     * 2. Extracts and sets relevant fields into a BookingDTO.
     *
     * @param booking the Booking entity to convert
     * @return a BookingDTO containing mapped booking data
     */
    private BookingDTO mapToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setCustomerFullName(booking.getCustomer().getFirstName() + " " + booking.getCustomer().getLastName());
        dto.setCustomerEmail(booking.getCustomer().getEmail());
        dto.setRoomNumber(booking.getRoom().getRoomNumber());
        dto.setBedNumber(booking.getBed().getBedNumber());
        dto.setBookingStatus(booking.getBookingStatus().toString());
        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setTotalPrice(booking.getTotalPrice());
        return dto;
    }
}
