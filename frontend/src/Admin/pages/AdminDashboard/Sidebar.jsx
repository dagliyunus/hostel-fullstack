import React from 'react';
import '../../../styles/admin/dashboard/sidebar.css';
import { FiLogOut } from 'react-icons/fi';

const Sidebar = ({ activeTab, setActiveTab }) => {
    const handleLogout = () => {
        localStorage.removeItem('adminId');
        window.location.href = '/admin/login';
    };

    return (
        <aside className="sidebar">
            <h2 className="sidebar-title">🏨 Admin Panel</h2>
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

            {/* 🔒 Log out section */}
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