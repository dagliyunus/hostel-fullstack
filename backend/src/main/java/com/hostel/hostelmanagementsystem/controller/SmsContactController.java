package com.hostel.hostelmanagementsystem.controller;

import com.hostel.hostelmanagementsystem.dto.ContactDTO;
import com.hostel.hostelmanagementsystem.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class SmsContactController {

    private final SmsService smsService;

    @Autowired
    public SmsContactController(SmsService smsService) {
        this.smsService = smsService;
    }

    /**
     * Handles POST requests to send a contact message via SMS.
     *
     * <p>This endpoint accepts a {@link ContactDTO} object in the request body containing
     * the name, email, and message from a contact form. It formats the message into a readable
     * SMS string and sends it to a pre-defined phone number (for example, the admin's verified
     * number via Twilio).</p>
     *
     * <p><strong>Request Format (Postman):</strong><br>
     * POST /api/contact/send-sms<br>
     * Body (application/json):<br>
     * {
     *   "name": "John Doe",
     *   "email": "john@example.com",
     *   "message": "Hello, this is a test message."
     * }</p>
     *
     * <p><strong>Flow:</strong>
     * <ul>
     *   <li>Extracts the contact information from the request body.</li>
     *   <li>Formats the message as an SMS string.</li>
     *   <li>Delegates the actual SMS sending to {@code SmsService.sendSms()}.</li>
     *   <li>Returns HTTP 200 with a success message if the operation completes.</li>
     * </ul>
     * </p>
     *
     * @param dto the contact details received from the frontend (name, email, message)
     * @return ResponseEntity indicating whether the message was successfully sent
     */
    @PostMapping("/send-sms")
    public ResponseEntity<?> handleContact(@RequestBody ContactDTO dto) {
        String msg = String.format(
                "📩 New message from %s (%s):\n%s",
                dto.getName(), dto.getEmail(), dto.getMessage()
        );

        // Send SMS to my own verified phone number (in Twilio trial)
        smsService.sendSms("+4917637672512", msg);

        return ResponseEntity.ok().body("Message sent successfully!");
    }
}
