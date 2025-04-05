import React from 'react';
import '../../styles/global/ServiceHighlights.css';
import { motion as Motion } from 'framer-motion';
import {FaWifi, FaShower, FaCoffee, FaLock, FaAddressBook} from 'react-icons/fa';

/**
 * @typedef {Object} Service
 * @property {JSX.Element} icon - The icon component representing the service.
 * @property {string} label - The descriptive label for the service.
 */

/**
 * Array of core services offered by the hostel.
 * Each service includes an icon and a textual label.
 *
 * @type {Service[]}
 */
const services = [
    { icon: <FaWifi />, label: 'Free Wi-Fi' },
    { icon: <FaShower />, label: 'Laundry' },
    { icon: <FaCoffee />, label: 'Breakfast' },
    { icon: <FaLock />, label: 'Secure Lockers' },
    { icon: <FaAddressBook />, label: 'Anmeldung' },
];

/**
 * ServiceHighlights Component
 *
 * Displays a grid of highlighted services provided by the hostel,
 * such as Wi-Fi, breakfast, laundry, lockers, and Anmeldung support.
 * Uses framer-motion to animate service cards on scroll into view.
 *
 * @component
 * @returns {JSX.Element} A section containing animated service cards.
 */
const ServiceHighlights = () => {
    return (
        <section className="service-highlights">
            <h2 className="section-title">Our Services</h2>
            <div className="service-list">
                {services.map((service, index) => (
                    <Motion.div
                        className="service-card"
                        key={index}
                        whileInView={{ scale: [0.8, 1], opacity: [0, 1] }}
                        viewport={{ once: true }}
                        transition={{ duration: 0.6, delay: index * 0.1 }}
                    >
                        <div className="icon">{service.icon}</div>
                        <p>{service.label}</p>
                    </Motion.div>
                ))}
            </div>
        </section>
    );
};

export default ServiceHighlights;