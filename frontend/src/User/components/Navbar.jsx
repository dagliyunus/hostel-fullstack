// src/User/components/Navbar.jsx
import React, {useState} from 'react';
import '../../styles/global/Navbar.css';

/**
 * Navbar Component
 *
 * Renders a vertically fixed navigation bar on the left side of the screen.
 * Provides anchor links to key sections of the homepage (Hero, Rooms, Booking, Gallery, Contact).
 *
 * Features:
 * - Fixed positioning on the vertical center-left of the viewport.
 * - Stylized brand title ("Inn-Berlin").
 * - Smooth scroll behavior when clicking links (if `scroll-behavior: smooth` is applied in CSS).
 * - Responsive and interactive layout designed for one-page navigation.
 *
 * Accessibility:
 * - Uses semantic `<nav>` and `<ul>` structure for better accessibility and screen reader support.
 *
 * @component
 * @returns {JSX.Element} A styled vertical navigation bar with internal anchor links.
 */
const Navbar = () => {
    const [menuOpen, setMenuOpen] = useState(false);

    return (
        <>
            {/* Top NavBar for Small Devices */}
            <div className="navbar-top">
                <div className="navbar-brand-horizontal">Inn-Berlin</div>
                <button className="menu-toggle" onClick={() => setMenuOpen(!menuOpen)}>
                    ☰
                </button>
            </div>

            {/* Sidebar for Desktop / Slide-in for Mobile */}
            <nav className={`navbar ${menuOpen ? 'navbar-mobile-open' : ''}`}>
                <div className="navbar-brand">Inn-Berlin</div>
                <ul className="navbar-links">
                    <li><a href="#hero">Home</a></li>
                    <li><a href="#rooms">Rooms</a></li>
                    <li><a href="#booking">Booking</a></li>
                    <li><a href="#gallery">Gallery</a></li>
                    <li><a href="#contact">Contact</a></li>
                </ul>
            </nav>
        </>
    );
};

export default Navbar;