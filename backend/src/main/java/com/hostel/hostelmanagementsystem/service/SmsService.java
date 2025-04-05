package com.hostel.hostelmanagementsystem.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    /**
     * Twilio account credentials and sender number used to initialize and send SMS messages.
     * <p>
     * These credentials are used to authenticate with the Twilio API.
     * FROM_NUMBER must be a verified Twilio phone number capable of sending SMS.
     */
    private static final String ACCOUNT_SID = "ACe6061fea4061249d8d1295cdb6708574";

    /**
     * Twilio account credentials and sender number used to initialize and send SMS messages.
     * <p>
     * These credentials are used to authenticate with the Twilio API.
     * FROM_NUMBER must be a verified Twilio phone number capable of sending SMS.
     */
    private static final String AUTH_TOKEN = "af2fc8935847979d5d9a1c3d6ccdb6c9";

    /**
     * Twilio account credentials and sender number used to initialize and send SMS messages.
     * <p>
     * These credentials are used to authenticate with the Twilio API.
     * FROM_NUMBER must be a verified Twilio phone number capable of sending SMS.
     */
    private static final String FROM_NUMBER = "+16199012838"; // my Twilio number that I bought

    /**
     * Static initialization block to configure the Twilio client with the account SID and authentication token.
     * <p>
     * This ensures Twilio is ready to send messages as soon as the class is loaded.
     */
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    /**
     * Sends an SMS message to the specified phone number using the Twilio API.
     * <p>
     * This method constructs and sends a message from the configured Twilio number to the provided recipient.
     * After successful sending, it logs the message SID to the console for tracking.
     *
     * @param to   the recipient's phone number in E.164 format (e.g., +1234567890)
     * @param body the content of the SMS message
     */
    public void sendSms(String to, String body) {
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(FROM_NUMBER),
                body
        ).create();

        System.out.println(" Sent SMS with SID: " + message.getSid());
    }
}
