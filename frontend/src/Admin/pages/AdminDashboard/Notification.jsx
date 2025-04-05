import React, { useState, useEffect } from "react";
import { getAllNotifications, markAsRead } from "../../services/AdminServices/notificationService";
import "../../../styles/admin/dashboard/notification.css";

const Notification = () => {
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [showAll, setShowAll] = useState(false);

    // 🔁 Fetch on mount
    useEffect(() => {
        fetchNotifications();
    }, []);

    const fetchNotifications = async () => {
        try {
            const response = await getAllNotifications();
            const data = Array.isArray(response.data) ? response.data : [];
            setNotifications(data);
        } catch (err) {
            console.error("Error fetching notifications:", err);
            setError("Failed to load notifications.");
        } finally {
            setLoading(false);
        }
    };

    // ✅ Mark as read & update local state without re-fetch
    const handleMarkAsRead = async (id) => {
        try {
            await markAsRead(id);
            setNotifications((prev) =>
                prev.map((n) => (n.id === id ? { ...n, isRead: true } : n))
            );
        } catch (err) {
            console.error("Failed to mark notification as read:", err);
        }
    };

    const sortedNotifications = [...notifications].sort(
        (a, b) => new Date(b.createdAt) - new Date(a.createdAt)
    );
    const visibleNotifications = showAll ? sortedNotifications : sortedNotifications.slice(0, 5);

    const timeAgo = (timestamp) => {
        const diff = Date.now() - new Date(timestamp).getTime();
        const minutes = Math.floor(diff / (1000 * 60));
        const hours = Math.floor(minutes / 60);
        const days = Math.floor(hours / 24);

        if (minutes < 1) return "Just now";
        if (minutes < 60) return `${minutes} minute(s) ago`;
        if (hours < 24) return `${hours} hour(s) ago`;
        return `${days} day(s) ago`;
    };

    return (
        <div className="notification-wrapper">
            <div className="notification-header">
                <h3>📌 Notifications</h3>
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
                                <th>Title</th>
                                <th>Message</th>
                                <th>Customer Full Name</th>
                                <th>Room No</th>
                                <th>Bed No</th>
                                <th>Check-In</th>
                                <th>Check-Out</th>
                                <th>€ Total Payment</th>
                                <th>Created</th>
                            </tr>
                            </thead>
                            <tbody>
                            {visibleNotifications.map((notification) => (
                                <tr
                                    key={notification.id}
                                    className={!notification.isRead ? "unread-notification" : ""}
                                    onClick={() => !notification.isRead && handleMarkAsRead(notification.id)}
                                >
                                    <td>{notification.title}</td>
                                    <td>{notification.message}</td>
                                    <td>{notification.customerFullName}</td>
                                    <td>{notification.roomNumber}</td>
                                    <td>{notification.bedNumber}</td>
                                    <td>{notification.checkInDate}</td>
                                    <td>{notification.checkOutDate}</td>
                                    <td>{notification.totalPrice + ".00"}</td>
                                    <td>{timeAgo(notification.createdAt)}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>

                    {notifications.length > 5 && (
                        <button className="see-all-button" onClick={() => setShowAll(!showAll)}>
                            {showAll ? "See Less" : "See All"}
                        </button>
                    )}
                </>
            )}
        </div>
    );
};

export default Notification;