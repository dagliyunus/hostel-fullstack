package com.hostel.hostelmanagementsystem.repository;

import com.hostel.hostelmanagementsystem.model.ContactMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    /**
     * Retrieves all contact messages that are marked as unread.
     * <p>
     * Purpose:
     * This method is used to fetch all contact messages that have not been read by the admin.
     * It is typically used in the admin dashboard to highlight new incoming messages.
     *
     * @return a list of unread {@link ContactMessage} entities
     */
    List<ContactMessage> findByIsReadFalse();

    /**
     * Persists a given {@link ContactMessage} entity in the database.
     * <p>
     * Purpose:
     * This method is used to save a new contact message or update an existing one.
     * It is typically invoked after a user submits a message through the contact form.
     *
     * @param contactMessage the contact message entity to be saved
     * @return the saved {@link ContactMessage} instance
     */
    @Override
    @NotNull
    @SuppressWarnings("unchecked")
    ContactMessage save(ContactMessage contactMessage);
}