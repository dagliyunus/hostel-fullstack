import React, { useEffect, useState } from "react";
import contactMessageService from "../../services/AdminServices/contactMessageService";
import "../../../styles/admin/dashboard/contactmessage.css";
import { format } from "date-fns";

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
            setError("❌ Failed to fetch messages.");
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
            console.error("❌ Failed to mark message as read");
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
                        <h2>📧 Message from {selectedMessage.name}</h2>
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