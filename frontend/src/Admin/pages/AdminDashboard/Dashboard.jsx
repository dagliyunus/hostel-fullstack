import React, { useState } from 'react';
import ManageRooms from './ManageRooms.jsx';
import ManageCustomers from './ManageCustomers.jsx';
import ManageBookings from './ManageBookings.jsx';
import "../../../styles/admin/dashboard/dashboard.css";
import Notification from "./Notification.jsx";
import ContactMessage from "./ContactMessage.jsx";

/**
 * Dashboard is the main administrative view component for the application.
 *
 * @component
 * @returns {JSX.Element} A container displaying the active dashboard tab content.
 *
 * @description
 * This component is responsible for rendering different admin management views based on the selected tab.
 * It defaults to the "overview" tab, which shows notifications and contact messages.
 * Tabs are handled via a `switch` statement in the `renderActiveTab` method.
 *
 * Currently supported tabs:
 * - 'overview'  ➝ Renders <Notification /> and <ContactMessage />
 * - 'rooms'     ➝ Renders <ManageRooms />
 * - 'customers' ➝ Renders <ManageCustomers />
 * - 'bookings'  ➝ Renders <ManageBookings />
 *
 * Note:
 * - Sidebar navigation is managed globally in `App.jsx`.
 * - `activeTab` is hardcoded to 'overview' but designed for dynamic tab selection in the future.
 */
const Dashboard = ({isSidebarOpen}) => {
    const [activeTab] = useState('overview');

    const renderActiveTab = () => {
        switch (activeTab) {
            case 'overview':
                return (
                    <div className={`dashboard-grid ${isSidebarOpen ? 'sidebar-open' : 'sidebar-closed'}`}>
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
