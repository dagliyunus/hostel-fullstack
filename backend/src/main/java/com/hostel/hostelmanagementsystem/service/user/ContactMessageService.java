package com.hostel.hostelmanagementsystem.service.user;

import com.hostel.hostelmanagementsystem.dto.ContactDTO;
import com.hostel.hostelmanagementsystem.model.ContactMessage;
import com.hostel.hostelmanagementsystem.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    @Autowired
    public ContactMessageService(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    /**
     * Saves a new contact message to the database.
     * <p>
     * Purpose: This method is used to persist a message submitted through the contact form.
     * It converts the {@link ContactDTO} object into a {@link ContactMessage} entity and saves it.
     * <p>
     * Flow:
     * 1. Accepts a DTO containing name, email, message, and read status.
     * 2. Maps the DTO to an entity.
     * 3. Saves the entity via the repository.
     *
     * @param dto the contact message data to be saved
     */
    public void saveMessage(ContactDTO dto) {
        ContactMessage message = new ContactMessage();
        message.setName(dto.getName());
        message.setEmail(dto.getEmail());
        message.setMessage(dto.getMessage());
        message.setRead(dto.isRead());
        contactMessageRepository.save(message);
    }

    /**
     * Retrieves all contact messages stored in the database.
     * <p>
     * Purpose: Useful for displaying all user-submitted messages on the admin dashboard.
     *
     * @return a list of all {@link ContactMessage} entries
     */
    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAll();
    }

    /**
     * Retrieves all unread contact messages.
     * <p>
     * Purpose: Useful for filtering and displaying only messages that have not yet been reviewed.
     *
     * @return a list of unread {@link ContactMessage} entries
     */
    public List<ContactMessage> getUnreadMessages() {
        return contactMessageRepository.findByIsReadFalse();
    }

    /**
     * Marks a specific contact message as read based on its ID.
     * <p>
     * Purpose: Allows admin users to mark messages as read after reviewing them.
     * <p>
     * Flow:
     * 1. Retrieves the message by ID.
     * 2. If present, updates its read status to true and saves the change.
     *
     * @param messageId the ID of the contact message to mark as read
     */
    public void markAsRead(Long messageId) {
        contactMessageRepository.findById(messageId).ifPresent(message -> {
            message.setRead(true);
            contactMessageRepository.save(message);
        });
    }
}