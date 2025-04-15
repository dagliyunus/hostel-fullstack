import React, { useState } from 'react';
import '../../styles/global/HeroSection.css';
import { motion as Motion } from 'framer-motion';
import { FaArrowDown } from 'react-icons/fa';

/**
 * HeroSection Component
 *
 * Displays the full-page hero section with background, headline, subtitle,
 * group booking prompt, and a booking form for check-in, check-out, and guest selection.
 *
 * Props:
 * @param {Function} setSearchCriteria - Callback function to pass the user's selected booking data (check-in, check-out, guests) to the parent component.
 *
 * State:
 * @state {string} checkIn - The selected check-in date.
 * @state {string} checkOut - The selected check-out date.
 * @state {number} guests - The number of guests (minimum 1).
 *
 * Functionality:
 * - Handles form submission by preventing default form behavior.
 * - Passes form data to `setSearchCriteria` to trigger a room availability search.
 * - Smooth-scrolls the page to the booking section when the form is submitted.
 *
 * Visual Features:
 * - Animated content with Framer Motion.
 * - Styled background with fixed image and scroll cue icon.
 * - Group booking promotion card with hover effect.
 *
 * @component
 * @returns {JSX.Element} Rendered hero section including booking form and animations.
 */
const HeroSection = ({ setSearchCriteria }) => {
    const [checkIn, setCheckIn] = useState('');
    const [checkOut, setCheckOut] = useState('');
    const [guests, setGuests] = useState(1);

    /**
     * Handles form submission:
     * - Prevents page reload
     * - Sends user input to the parent via `setSearchCriteria`
     * - Scrolls to booking section
     *
     * @param {React.FormEvent<HTMLFormElement>} e - Form submit event
     */
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
            <div className="hero-bg"  />

            {/* Hero Main Content */}
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

            {/* Group Booking Info Card (Moved Below for Mobile) */}
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

            <div className="scroll-down-cue">
                <FaArrowDown className="scroll-icon" />
            </div>
        </section>
    );
};

export default HeroSection;