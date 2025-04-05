package com.hostel.hostelmanagementsystem.service.user;

import com.hostel.hostelmanagementsystem.dto.user.CreateUserBookingDTO;
import com.hostel.hostelmanagementsystem.dto.user.UserBookingDTO;
import com.hostel.hostelmanagementsystem.enums.BookingStatus;
import com.hostel.hostelmanagementsystem.enums.PaymentType;
import com.hostel.hostelmanagementsystem.model.*;
import com.hostel.hostelmanagementsystem.repository.*;
import com.hostel.hostelmanagementsystem.service.MailService;
import com.hostel.hostelmanagementsystem.service.PaymentService;
import com.hostel.hostelmanagementsystem.service.SmsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserBookingService {

    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final NotificationRepository notificationRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final SmsService smsService;
    private final MailService mailService;

    @Autowired
    public UserBookingService(RoomRepository roomRepository,
                              BedRepository bedRepository,
                              CustomerRepository customerRepository,
                              BookingRepository bookingRepository,
                              NotificationRepository notificationRepository,
                              PaymentRepository paymentRepository,
                              PaymentService paymentService, SmsService smsService, MailService mailService) {
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.notificationRepository = notificationRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
        this.smsService = smsService;
        this.mailService = mailService;
    }

    /**
     * Handles the creation of a new booking made by a user.
     * <p>
     * This transactional method performs the full lifecycle of a user booking:
     * - Locates the room by room number.
     * - Identifies the first unoccupied bed in the specified room.
     * - Registers a new customer with their details and assigns them to the room and bed.
     * - Creates and persists a new Booking entity for the customer.
     * - Generates a Notification entry summarizing the booking.
     * - Issues a new Payment record linked to the booking.
     * - Sends confirmation email and SMS to the user.
     * <p>
     * This process is transactional to ensure atomicity; if any part fails, the whole operation rolls back.
     *
     * @param dto the booking details provided by the user
     * @return a {@link UserBookingDTO} containing key information about the created booking
     * @throws Exception if any error occurs during booking creation, such as no available room/bed
     */
    @Transactional
    public UserBookingDTO createBooking(CreateUserBookingDTO dto) throws Exception {
        Room room = roomRepository.findByRoomNumber(dto.getRoomNumber())
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + dto.getRoomNumber()));

        Bed availableBed = bedRepository.findByRoom(room).stream()
                .filter(b -> !customerRepository.existsByBed(b))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No available beds in room: " + dto.getRoomNumber()));

        Customer customer = new Customer();
        customer.setCustomerId(generateCustomerId());
        customer.setFirstName(dto.getCustomerFirstName());
        customer.setLastName(dto.getCustomerLastName());
        customer.setEmail(dto.getCustomerEmail());
        customer.setPhone(dto.getCustomerPhone());
        customer.setDateOfBirth(dto.getCustomerDateOfBirth());
        customer.setRoom(room);
        customer.setBed(availableBed);
        customer.setRegisteredAt(LocalDateTime.now());
        customerRepository.save(customer);

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setBed(availableBed);
        booking.setBookingStatus(BookingStatus.Booked);
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setTotalPrice(dto.getTotalPrice());
        bookingRepository.save(booking);

        Notification notification = new Notification();
        notification.setCustomerFullName(customer.getFirstName() + " " + customer.getLastName());
        notification.setBookingId(booking.getBookingId());
        notification.setRoomNumber(room.getRoomNumber());
        notification.setBedNumber(availableBed.getBedNumber());
        notification.setTitle("New Booking");
        notification.setMessage("New booking created by user.");
        notification.setIsRead(false);
        notification.setCheckInDate(booking.getCheckInDate());
        notification.setCheckOutDate(booking.getCheckOutDate());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setTotalPrice(dto.getTotalPrice());
        notificationRepository.save(notification);

        String nextPaymentId = paymentService.generateNextPaymentId();

        Payment payment = new Payment();
        payment.setPaymentId(nextPaymentId);
        payment.setBooking(booking);
        payment.setPaymentType(PaymentType.CREDIT_CARD);
        payment.setAmount(dto.getTotalPrice());
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);

        UserBookingDTO userBookingDTO = new UserBookingDTO();
        userBookingDTO.setBookingId(booking.getBookingId());
        userBookingDTO.setCustomerFullName(customer.getFirstName() + " " + customer.getLastName());
        userBookingDTO.setRoomNumber(room.getRoomNumber());
        userBookingDTO.setCheckInDate(dto.getCheckInDate());
        userBookingDTO.setCheckOutDate(dto.getCheckOutDate());
        userBookingDTO.setTotalPrice(dto.getTotalPrice());
        userBookingDTO.setPaymentId(nextPaymentId);

        String emailSubject = "Booking Confirmation - Inn Berlin Hostel";
        String emailText = String.format(
                "Hello %s,\n\nYour booking has been confirmed.\n\nDetails:\n" +
                        "Room: %s\nBed: %s\nCheck-In: %s\nCheck-Out: %s\nTotal Price: €%.2f\n\n" +
                        "Thank you for choosing Inn Berlin Hostel!",
                customer.getFirstName(),
                room.getRoomNumber(),
                availableBed.getBedNumber(),
                dto.getCheckInDate(),
                dto.getCheckOutDate(),
                dto.getTotalPrice()
        );

        mailService.sendConfirmation(customer.getFirstName(), customer.getEmail(), emailSubject, emailText);

        String smsMessage = String.format(
                "✅ Hello %s, your booking (Room %s, Bed %s) from %s to %s is confirmed. Total: €%.2f",
                customer.getFirstName(),
                room.getRoomNumber(),
                availableBed.getBedNumber(),
                dto.getCheckInDate(),
                dto.getCheckOutDate(),
                dto.getTotalPrice()
        );
        smsService.sendSms(customer.getPhone(), smsMessage);


        return userBookingDTO;
    }

    /**
     * Generates a unique customer ID based on the current number of customers in the system.
     * <p>
     * Purpose:
     * Ensures each customer receives a unique ID by appending the total customer count + 1
     * to a prefix "C". This approach is simple and suitable for systems where concurrent
     * creation is not expected to cause conflicts.
     * <p>
     * Example:
     * If there are currently 25 customers, the next generated ID will be "C26".
     *
     * @return a newly generated customer ID string (e.g., "C1", "C2", ...)
     */
    private String generateCustomerId() {
        long count = customerRepository.count();
        return "C" + (count + 1);
    }
}
