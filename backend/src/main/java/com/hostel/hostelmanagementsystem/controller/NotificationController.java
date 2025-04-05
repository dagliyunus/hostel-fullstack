package com.hostel.hostelmanagementsystem.controller;

import com.hostel.hostelmanagementsystem.dto.NotificationDTO;
import com.hostel.hostelmanagementsystem.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/dashboard/manageNotifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Retrieves all notifications from the system.
     * <p>
     * Request Format:
     * GET /api/admin/dashboard/manageNotifications
     * <p>
     * Purpose:
     * This method returns all notification records, regardless of their read status.
     * Useful for displaying a complete list of notifications in the admin dashboard.
     *
     * @return ResponseEntity containing a list of NotificationDTOs
     */
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    /**
     * Retrieves only unread notifications.
     * <p>
     * Request Format:
     * GET /api/admin/dashboard/manageNotifications/unread
     * <p>
     * Purpose:
     * This endpoint filters and returns only the notifications that have not yet been marked as read.
     * Useful for highlighting new or pending alerts to the admin.
     *
     * @return ResponseEntity containing a list of unread NotificationDTOs
     */
    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications() {
        return ResponseEntity.ok(notificationService.getUnreadNotifications());
    }

    /**
     * Marks a specific notification as read.
     * <p>
     * Request Format:
     * PATCH /api/admin/dashboard/manageNotifications/{id}/markAsRead
     * <p>
     * Purpose:
     * Allows the admin to mark a given notification as read by its unique identifier.
     * <p>
     * Flow:
     * - Receives the ID of the notification as a path variable.
     * - Delegates the operation to the NotificationService to update the status.
     *
     * @param id the ID of the notification to be marked as read
     * @return ResponseEntity with HTTP 200 OK if successful
     */
    @PatchMapping("/{id}/markAsRead")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a specific notification.
     * <p>
     * Request Format:
     * DELETE /api/admin/dashboard/manageNotifications/{id}
     * <p>
     * Purpose:
     * This method allows the admin to permanently delete a notification by its ID.
     * <p>
     * Flow:
     * - Accepts the ID of the notification as a path variable.
     * - Invokes the service layer to perform deletion.
     *
     * @param id the ID of the notification to delete
     * @return ResponseEntity with HTTP 204 No Content if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}