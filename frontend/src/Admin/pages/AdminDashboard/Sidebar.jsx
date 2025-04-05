import React from 'react';
import '../../../styles/admin/dashboard/sidebar.css';
import { FiLogOut } from 'react-icons/fi';

/**
 * Sidebar component for the Admin Panel UI.
 *
 * @component
 * @param {Object} props - Component props.
 * @param {string} props.activeTab - The currently active tab identifier (e.g., 'overview', 'rooms').
 * @param {function} props.setActiveTab - Callback to update the active tab when a menu item is clicked.
 * @returns {JSX.Element} A sidebar navigation menu with links to different admin dashboard sections and a logout button.
 *
 * @description
 * This component renders a vertical navigation menu used to switch between different admin panel views.
 * It highlights the currently selected tab using a conditional className.
 * Includes a logout section that clears `adminId` from localStorage and redirects to the login page.
 *
 * Features:
 * - Navigation for admin sections: Overview, Manage Rooms, Customers, Bookings, Payments.
 * - Active tab highlighting using `activeTab` comparison.
 * - Secure logout functionality via `handleLogout()`.
 *
 * Dependencies:
 * - `react-icons/fi` for logout icon (`FiLogOut`)
 * - `localStorage` and `window.location` for session control
 */
const Sidebar = ({ activeTab, setActiveTab }) => {
    const handleLogout = () => {
        localStorage.removeItem('adminId');
        window.location.href = '/admin/login';
    };

    return (
        <aside className="sidebar">
            <h2 className="sidebar-title"> Admin Panel</h2>
            <ul className="sidebar-nav">
                <li onClick={() => setActiveTab('overview')} className={activeTab === 'overview' ? 'active' : ''}>
                    Overview
                </li>
                <li onClick={() => setActiveTab('rooms')} className={activeTab === 'rooms' ? 'active' : ''}>
                    Manage Rooms
                </li>
                <li onClick={() => setActiveTab('customers')} className={activeTab === 'customers' ? 'active' : ''}>
                    Manage Customers
                </li>
                <li onClick={() => setActiveTab('bookings')} className={activeTab === 'bookings' ? 'active' : ''}>
                    Manage Bookings
                </li>
                <li onClick={() => setActiveTab('payments')} className={activeTab === 'payments' ? 'active' : ''}>
                    Manage Payments
                </li>
            </ul>

            {/* Log out section */}
            <div className="logout-section">
                <button onClick={handleLogout} className="logout-btn">
                    <FiLogOut className="logout-icon" />
                    Log Out
                </button>
            </div>
        </aside>
    );
};

export default Sidebar;