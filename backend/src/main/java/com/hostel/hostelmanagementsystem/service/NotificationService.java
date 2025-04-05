package com.hostel.hostelmanagementsystem.service;

import com.hostel.hostelmanagementsystem.dto.BookingDTO;
import com.hostel.hostelmanagementsystem.dto.NotificationDTO;
import com.hostel.hostelmanagementsystem.model.Notification;
import com.hostel.hostelmanagementsystem.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService{

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Retrieves all notifications from the system.
     *
     * <p>This method fetches all {@link Notification} entities from the database
     * and converts them to {@link NotificationDTO} objects.
     *
     * @return a list of all notifications as {@link NotificationDTO}
     */
    public List<NotificationDTO> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();

        return notifications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all unread notifications from the system.
     *
     * <p>This method fetches all {@link Notification} entities marked as unread
     * and maps them to {@link NotificationDTO}.
     *
     * @return a list of unread notifications as {@link NotificationDTO}
     */
    public List<NotificationDTO> getUnreadNotifications() {
        List<Notification>  unread = notificationRepository.findByIsReadFalse();

        return unread.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Marks a specific notification as read.
     *
     * <p>This method locates a {@link Notification} by its ID and updates its
     * {@code isRead} status to {@code true}.
     *
     * @param id the ID of the notification to be marked as read
     * @throws IllegalArgumentException if no notification is found with the given ID
     */
    public void markAsRead(Long id){
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with id: " + id ));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    /**
     * Creates and saves a new notification based on booking information.
     *
     * <p>This method constructs a new {@link Notification} using the details provided in
     * the {@link BookingDTO}, such as booking ID, customer information, room details,
     * and stores it in the database.
     *
     * @param dto the booking data used to create the notification
     */
    public void createNotification(BookingDTO dto) {
        Notification notification = new Notification();
        notification.setBookingId(dto.getBookingId());
        notification.setCustomerFullName(dto.getCustomerFullName());
        notification.setBookingId(dto.getBookingId());
        notification.setTitle("New Booking");
        notification.setMessage("A new booking has been made by " + dto.getCustomerFullName());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRoomNumber(dto.getRoomNumber());
        notification.setBedNumber(dto.getBedNumber());
        notification.setCheckInDate(dto.getCheckInDate());
        notification.setCheckOutDate(dto.getCheckOutDate());
        notification.setTotalPrice(dto.getTotalPrice());

        notificationRepository.save(notification);
    }

    /**
     * Deletes a notification by its ID.
     *
     * <p>This method checks for the existence of the notification before deletion
     * and removes it from the database if found.
     *
     * @param id the ID of the notification to be deleted
     * @throws IllegalArgumentException if the notification does not exist
     */
    public void deleteNotification(Long id) {
        if(!notificationRepository.existsById(id)){
            throw new IllegalArgumentException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }

    /**
     * Converts a {@link Notification} entity to a {@link NotificationDTO}.
     *
     * <p>This method extracts relevant fields from the Notification entity and sets
     * them into a new NotificationDTO instance for data transfer purposes.
     *
     * @param notification the Notification entity to be converted
     * @return a fully populated NotificationDTO
     */
    private NotificationDTO mapToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getNotificationId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setIsRead(notification.getIsRead());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setCustomerFullName(notification.getCustomerFullName());
        dto.setRoomNumber(notification.getRoomNumber());
        dto.setBedNumber(notification.getBedNumber());
        dto.setCheckInDate(notification.getCheckInDate());
        dto.setCheckOutDate(notification.getCheckOutDate());
        dto.setTotalPrice(notification.getTotalPrice());
        return dto;
    }
}

