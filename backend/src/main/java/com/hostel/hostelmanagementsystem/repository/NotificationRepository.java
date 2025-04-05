package com.hostel.hostelmanagementsystem.repository;

import com.hostel.hostelmanagementsystem.model.Notification;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Retrieves all notification records from the database.
     * <p>
     * Purpose:
     * This method fetches a complete list of all Notification entities stored in the system.
     * It is typically used for administrative purposes, such as displaying all notifications
     * in the dashboard or performing bulk operations.
     *
     * @return a non-null list of all Notification entities
     */
    @Override
    @NonNull
    List<Notification> findAll();

    /**
     * Retrieves all unread notifications.
     * <p>
     * Purpose:
     * This method filters and returns only the notifications that have not yet been marked as read.
     * It is useful for highlighting or counting new updates or alerts for the user.
     *
     * @return a list of Notification entities where isRead is false
     */
    List<Notification> findByIsReadFalse();

    /**
     * Deletes a notification identified by its ID.
     * <p>
     * Purpose:
     * This method allows for the removal of a specific notification from the database,
     * typically when it is no longer relevant or has been processed.
     *
     * @param id the ID of the notification to delete
     */
    void deleteById(Long id);
}
