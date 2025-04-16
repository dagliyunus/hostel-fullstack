package com.hostel.hostelmanagementsystem.service;

import io.mailtrap.client.MailtrapClient;
import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.MailtrapMail;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MailService {

    private final MailtrapClient client;

    /**
     * Initializes the MailService with Mailtrap sandbox configuration.
     * <p>
     * Purpose: This constructor sets up a MailtrapClient instance for sending emails using
     * sandbox credentials. The configuration includes the inbox ID and API token
     * which are required to authenticate and send messages via Mailtrap's API.
     * <p>
     * Flow:
     * - A MailtrapConfig object is built using sandbox mode, inbox ID, and token.
     * - A MailtrapClient is instantiated using the factory and stored for later use.
     */
    public MailService() {
        MailtrapConfig config = new MailtrapConfig.Builder()
                .sandbox(true)
                .inboxId(3448305L) // my inbox id
                .token("d580d87f96f1b517690c64bade0c0840") // my token
                .build();
        this.client = MailtrapClientFactory.createMailtrapClient(config);
    }


    /**
     * Sends a booking confirmation email to the specified recipient.
     * <p>
     * Purpose: This method constructs and sends a formatted email message with
     * booking confirmation details, using Mailtrap's email sending API.
     * <p>
     * Flow:
     * - Constructs a MailtrapMail object with sender, recipient, subject, body, and category.
     * - Sends the email using the configured Mailtrap client.
     *
     * @param name the name of the recipient
     * @param email the recipient's email address
     * @param emailSubject the subject line of the email
     * @param emailText the message body to include in the email
     * @throws Exception if the email could not be sent
     */    public void sendConfirmation( String name, String email, String emailSubject, String emailText) throws Exception {
        MailtrapMail mail = MailtrapMail.builder()
                .from(new Address("hello@example.com", "InnBerlin Booking"))
                .to(List.of(new Address(email)))
                .subject("🧾 Booking Confirmation - InnBerlin Hostel")
                .text("Name: " + name + "\nEmail: " + email + "\n\nEmail Subject: " + emailSubject + "\n\nMessage:\n" +emailText )
                .category("Booking")
                .build();

        client.send(mail);
    }
}