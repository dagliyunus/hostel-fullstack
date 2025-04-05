package com.hostel.hostelmanagementsystem.controller.user;

import com.hostel.hostelmanagementsystem.dto.ContactDTO;
import com.hostel.hostelmanagementsystem.model.ContactMessage;
import com.hostel.hostelmanagementsystem.service.MailService;
import com.hostel.hostelmanagementsystem.service.user.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin
public class UserContactController {


    private final MailService mailService;
    private final ContactMessageService contactMessageService;

    @Autowired
    public UserContactController(MailService mailService, ContactMessageService contactMessageService) {
        this.mailService = mailService;
        this.contactMessageService = contactMessageService;
    }

    /**
     * Handles HTTP POST requests to save a new contact message.
     *
     * <p>This endpoint accepts a {@link ContactDTO} request body and attempts to save the message
     * using the {@code contactMessageService}. If successful, it returns an HTTP 200 (OK) with a success message.
     * If an unexpected error occurs, it returns an HTTP 500 (Internal Server Error) with the error message.</p>
     *
     * @param dto the contact message data provided by the client
     * @return a {@link ResponseEntity} containing a success or error message
     * @throws RuntimeException if an unexpected error occurs during message saving
     */
    @PostMapping("/send-email")
    public ResponseEntity<String> saveMessage(@RequestBody ContactDTO dto) {
        try {
            contactMessageService.saveMessage(dto);
            return ResponseEntity.ok("Message saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to save message: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests to retrieve all contact messages.
     *
     * <p>This endpoint is intended for admin use, fetching all messages stored in the system.
     * It uses the {@code contactMessageService} to retrieve the list of {@link ContactMessage} objects
     * and returns them with an HTTP 200 (OK) status.</p>
     *
     * @return a {@link ResponseEntity} containing the list of all contact messages
     */
    @GetMapping("/all")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        List<ContactMessage> messages = contactMessageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    /**
     * Handles HTTP PUT requests to mark a contact message as read.
     *
     * <p>This endpoint receives a message ID as a path variable and uses the {@code contactMessageService}
     * to update the message's read status. If the operation is successful, it returns HTTP 200 (OK).
     * If the message is not found, it returns HTTP 404 (Not Found).</p>
     *
     * @param id the ID of the contact message to be marked as read
     * @return a {@link ResponseEntity} containing a success or error message
     */
    @PutMapping("/mark-as-read/{id}")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        try {
            contactMessageService.markAsRead(id);
            return ResponseEntity.ok("Message marked as read.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Message not found.");
        }
    }

    /**
     * Handles HTTP POST requests to send a booking confirmation email.
     *
     * <p>This endpoint accepts four request parameters—name, email, subject, and text—used to
     * compose and send a booking confirmation email to the specified recipient. It utilizes
     * the {@code MailService} to send the email and returns a success or failure message based
     * on the outcome.</p>
     *
     * <p>Request Format (Postman):
     * POST /api/contact/send-confirmation
     * Query Params:
     * - name: The recipient's name
     * - email: The recipient's email address
     * - emailSubject: The subject of the email
     * - emailText: The body content of the email</p>
     *
     * <p>Flow:
     * 1. Accepts request parameters required for the email content.
     * 2. Delegates the actual sending to {@code mailService.sendConfirmation()}.
     * 3. If successful, returns HTTP 200 with confirmation.
     * 4. If failure occurs, returns HTTP 500 with error message.</p>
     *
     * @param name the name of the recipient
     * @param email the email address of the recipient
     * @param emailSubject the subject of the confirmation email
     * @param emailText the body content of the confirmation email
     * @return ResponseEntity indicating success or failure of email sending
     */
    @PostMapping("/send-confirmation")
    public ResponseEntity<String> sendBookingConfirmation(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String emailSubject,
            @RequestParam String emailText
    ) {
        try {
            mailService.sendConfirmation(name, email, emailSubject, emailText);
            return ResponseEntity.ok(" Confirmation email sent!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(" Failed to send email: " + e.getMessage());
        }
    }
}