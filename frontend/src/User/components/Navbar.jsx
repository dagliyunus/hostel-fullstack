// src/User/components/Navbar.jsx
import React from 'react';
import '../../styles/global/Navbar.css';

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-brand">Inn-Berlin</div>
            <ul className="navbar-links">
                <li><a href="#hero">Home</a></li>
                <li><a href="#rooms">Rooms</a></li>
                <li><a href="#booking">Booking</a></li>
                <li><a href="#gallery">Gallery</a></li>
                <li><a href="#contact">Contact</a></li>
            </ul>
        </nav>
    );
};

export default Navbar;