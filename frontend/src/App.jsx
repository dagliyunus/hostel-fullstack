import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import HomePage from './User/components/HomePage';

import AdminLogin from './Admin/pages/AdminLoginPage/AdminLogin';
import Dashboard from './Admin/pages/AdminDashboard/Dashboard';
import ManageRooms from './Admin/pages/AdminDashboard/ManageRooms';
import ManageCustomers from './Admin/pages/AdminDashboard/ManageCustomers';
import ManageBookings from './Admin/pages/AdminDashboard/ManageBookings';
import ManagePayment from './Admin/pages/AdminDashboard/ManagePayment';
import Sidebar from './Admin/pages/AdminDashboard/Sidebar';

import './styles/admin/dashboard/sidebar.css';
import './styles/admin/dashboard/dashboard.css';
import './styles/admin/managementTables/style.css';
import './styles/admin/dashboard/payment.css';
import './styles/admin/adminLoginPage/adminLogin.css';
import {FiMenu, FiX} from "react-icons/fi";

/**
 * AdminPanel Component
 *
 * Renders the main administrative dashboard interface, which includes a sidebar for navigation
 * and a main content area that dynamically renders the selected tab's content.
 *
 * Tabs include:
 * - Overview (Dashboard)
 * - Manage Rooms
 * - Manage Customers
 * - Manage Bookings
 * - Manage Payments
 *
 * @component
 * @returns {JSX.Element} Admin interface with sidebar and dynamic content rendering.
 */
const AdminPanel = () => {
    const [activeTab, setActiveTab] = useState('overview');
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const toggleSidebar = () => {
        setIsSidebarOpen(prev => !prev);
    }

    /**
     * Returns the component to be rendered based on the currently selected tab.
     *
     * @function
     * @returns {JSX.Element} Component for the selected tab.
     */
    const renderTab = () => {
        const commonProps ={isSidebarOpen};
        switch (activeTab) {
            case 'overview':
                return <Dashboard {...commonProps} activeTab={activeTab} />;
            case 'rooms':
                return <ManageRooms {...commonProps} />;
            case 'customers':
                return <ManageCustomers {...commonProps}/>;
            case 'bookings':
                return <ManageBookings {...commonProps}/>;
            case 'payments':
                return <ManagePayment {...commonProps}/>;
            default:
                return <Dashboard {...commonProps}/>;
        }
    };

    return (
        <div className="admin-app-container">
            <button className="sidebar-toggle-btn" onClick={toggleSidebar}>
                {isSidebarOpen ? <FiX /> : <FiMenu />}
            </button>
            <Sidebar
                activeTab={activeTab}
                setActiveTab={setActiveTab}
                isOpen={isSidebarOpen}
                setIsOpen={setIsSidebarOpen}
            />
            <main className={`admin-main-content ${isSidebarOpen ? 'sidebar-open' : 'sidebar-closed'}`}>
                {renderTab()}
            </main>
        </div>
    );
};

/**
 * App Component
 *
 * Main entry point for the React application. Handles routing using `react-router-dom`.
 * Includes both public and protected routes:
 *
 * - `/` — Public Home Page
 * - `/admin/login` — Admin login page, redirects to dashboard if already logged in.
 * - `/admin/dashboard` — Protected admin panel, only accessible if logged in.
 * - Catch-all route redirects all unknown paths to homepage.
 *
 * @component
 * @returns {JSX.Element} App router structure and authentication-based redirection.
 */
function App() {
    const [loggedIn, setLoggedIn] = useState(!!localStorage.getItem('adminId'));

    useEffect(() => {
        setLoggedIn(!!localStorage.getItem('adminId'));
    }, []);

    return (
        <Router>
            <Routes>
                {/*  Public Home Page Route */}
                <Route path="/" element={<HomePage />} />

                {/* Public Admin Login Route */}
                <Route
                    path="/admin/login"
                    element={
                        loggedIn ? (
                            <Navigate to="/admin/dashboard" replace />
                        ) : (
                            <AdminLogin onLoginSuccess={() => setLoggedIn(true)} />
                        )
                    }
                />

                {/* Protected Dashboard Route */}
                <Route
                    path="/admin/dashboard"
                    element={
                        loggedIn ? <AdminPanel /> : <Navigate to="/admin/login" replace />
                    }
                />

                {/* Catch-all: redirect to homepage instead of admin login */}
                <Route path="*" element={<Navigate to="/" replace />} />
            </Routes>
        </Router>
    );
}

export default App;