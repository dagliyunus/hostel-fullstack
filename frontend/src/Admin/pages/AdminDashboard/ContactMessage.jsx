import React, { useEffect, useState } from "react";
import contactMessageService from "../../services/AdminServices/contactMessageService";
import "../../../styles/admin/dashboard/contactmessage.css";
import { format } from "date-fns";

/**
 * ContactMessage is a React functional component used for displaying a list of contact form messages.
 * It fetches messages from the backend, allows marking them as read, and displays full details in a modal.
 *
 * @component
 * @returns {JSX.Element} The rendered contact message section containing the message table and optional modal.
 *
 * @description
 * Features:
 * - Fetches all contact messages from the server on mount using `contactMessageService.getAllMessages()`.
 * - Supports viewing only the latest 5 messages or toggling to see all.
 * - Allows marking individual messages as "read", which updates both backend and frontend state.
 * - Shows message content inside a modal popup when "View" is clicked.
 * - Handles loading and error states gracefully.
 *
 * State Variables:
 * - `messages` (array): List of contact messages fetched from the server.
 * - `loading` (boolean): Indicates whether the messages are currently being loaded.
 * - `error` (string): Holds error messages in case of a failed fetch.
 * - `showAll` (boolean): Toggles between showing only the latest 5 messages or all messages.
 * - `selectedMessage` (object|null): Stores the currently selected message to display in a modal.
 * - `showModal` (boolean): Controls visibility of the modal.
 *
 * Dependencies:
 * - `contactMessageService` provides methods to fetch and update message data.
 * - `date-fns` for date formatting.
 */

const ContactMessage = () => {
    const [messages, setMessages] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [showAll, setShowAll] = useState(false);
    const [selectedMessage, setSelectedMessage] = useState(null);
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        fetchMessages();
    }, []);

    const fetchMessages = async () => {
        try {
            const response = await contactMessageService.getAllMessages();
            setMessages(response.data.reverse());
        } catch {
            setError(" Failed to fetch messages.");
        } finally {
            setLoading(false);
        }
    };

    const handleMarkAsRead = async (id, message) => {
        try {
            await contactMessageService.markAsRead(id);
            setMessages((prev) =>
                prev.map((msg) =>
                    msg.id === id ? { ...msg, read: true } : msg
                )
            );
            setSelectedMessage(message);
            setShowModal(true);
        } catch {
            console.error(" Failed to mark message as read");
        }
    };

    const closeModal = () => {
        setSelectedMessage(null);
        setShowModal(false);
    };

    const visibleMessages = showAll ? messages : messages.slice(0, 5);

    return (
        <div className="contact-message-wrapper">
            <div className="contact-message-header">
                <h3>📥 Contact Messages</h3>
            </div>

            {loading ? (
                <p>Loading...</p>
            ) : error ? (
                <p className="error">{error}</p>
            ) : (
                <>
                    <div className={`responsive-table-wrapper notification-transition ${showAll ? "" : "collapsed"}`}>
                        <table className="notification-table">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Received</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            {visibleMessages.map((msg) => (
                                <tr
                                    key={msg.id}
                                    className={!msg.read ? "unread-notification" : ""}
                                >
                                    <td>{msg.name}</td>
                                    <td>{msg.email}</td>
                                    <td>{format(new Date(msg.sentAt), "PPpp")}</td>
                                    <td>
                                        <button onClick={() => handleMarkAsRead(msg.id, msg)}>
                                            View
                                        </button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>

                    {messages.length > 5 && (
                        <button className="see-all-button" onClick={() => setShowAll(!showAll)}>
                            {showAll ? "See Less" : "See All"}
                        </button>
                    )}
                </>
            )}

            {selectedMessage && showModal && (
                <div className="modal-backdrop" onClick={closeModal}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <h2> Message from {selectedMessage.name}</h2>
                        <p><strong>Email:</strong> {selectedMessage.email}</p>
                        <p><strong>Sent At:</strong> {format(new Date(selectedMessage.sentAt), "PPpp")}</p>
                        <hr />
                        <p>{selectedMessage.message}</p>
                        <button onClick={closeModal}>Close</button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ContactMessage;