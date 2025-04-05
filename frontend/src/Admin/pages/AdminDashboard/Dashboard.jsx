import React, { useState } from 'react';
import ManageRooms from './ManageRooms.jsx';
import ManageCustomers from './ManageCustomers.jsx';
import ManageBookings from './ManageBookings.jsx';
import "../../../styles/admin/dashboard/dashboard.css";
import Notification from "./Notification.jsx";
import ContactMessage from "./ContactMessage.jsx"; // ✅ Correct path


const Dashboard = () => {
    const [activeTab] = useState('overview');

    const renderActiveTab = () => {
        switch (activeTab) {
            case 'overview':
                return (
                    <div className="dashboard-grid">
                        <Notification />
                        <ContactMessage />
                    </div>
                );
            case 'rooms':
                return <ManageRooms />;
            case 'customers':
                return <ManageCustomers />;
            case 'bookings':
                return <ManageBookings />;
            default:
                return null;
        }
    };

    return (
        <div className="admin-dashboard-container">
            {/* Sidebar is handled globally in App.jsx */}
            <main className="dashboard-content">
                {renderActiveTab()}
            </main>
        </div>
    );
};

export default Dashboard;
