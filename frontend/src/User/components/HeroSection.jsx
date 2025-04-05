import React, { useState } from 'react';
import '../../styles/global/HeroSection.css';
import { motion as Motion } from 'framer-motion';
import { FaArrowDown } from 'react-icons/fa';

const HeroSection = ({ setSearchCriteria }) => {
    const [checkIn, setCheckIn] = useState('');
    const [checkOut, setCheckOut] = useState('');
    const [guests, setGuests] = useState(1);

    const handleSubmit = (e) => {
        e.preventDefault();
        setSearchCriteria({ checkIn, checkOut, guests });
        const bookingSection = document.getElementById('booking');
        if (bookingSection) {
            bookingSection.scrollIntoView({ behavior: 'smooth' });
        }
    };

    return (
        <section className="hero-section">
            <div className="hero-bg" />

            {/* Group Booking Info Card */}
            <Motion.div
                className="group-booking-card"
                initial={{ opacity: 0, y: -10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.8, ease: 'easeOut' }}
                whileHover={{ scale: 1.03 }}
            >
                <p className="group-text">
                     <strong>Planning a group trip?</strong><br />
                    <a href="#contact">Contact us</a> for exclusive group rates & comfort!
                </p>
            </Motion.div>

            <Motion.div
                className="hero-content"
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 1 }}
            >
                <h1 className="hero-title">Welcome to Inn-Berlin Hostel</h1>
                <p className="hero-subtitle">Modern comfort for global travelers</p>
                <form className="booking-form" onSubmit={handleSubmit}>
                    <input
                        type="date"
                        placeholder="Check-in"
                        value={checkIn}
                        onChange={(e) => setCheckIn(e.target.value)}
                        required
                    />
                    <input
                        type="date"
                        placeholder="Check-out"
                        value={checkOut}
                        onChange={(e) => setCheckOut(e.target.value)}
                        required
                    />
                    <input
                        type="number"
                        placeholder="Guests"
                        min="1"
                        value={guests}
                        onChange={(e) => setGuests(e.target.value)}
                        required
                    />
                    <button type="submit">Book Your Stay</button>
                </form>
            </Motion.div>

            <div className="scroll-down-cue">
                <FaArrowDown className="scroll-icon" />
            </div>
        </section>
    );
};

export default HeroSection;