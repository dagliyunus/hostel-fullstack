import React from 'react';
import '../../styles/global/HostelMap.css';
import { motion as Motion } from 'framer-motion';
import { FaLandmark, FaTree, FaSubway } from 'react-icons/fa';
/**
* List of nearby attractions for the floating icon overlay on the map.
*
* @constant
* @type {Array<Object>}
* @property {JSX.Element} icon - Icon representing the type of attraction (e.g., landmark, park, subway).
* @property {string} label - Display name of the attraction.
* @property {number} offset - Vertical animation offset in pixels (used for entrance animation).
*/
const attractions = [
    { icon: <FaLandmark />, label: 'Museums', offset: -30 },
    { icon: <FaTree />, label: 'City Park', offset: 10 },
    { icon: <FaSubway />, label: 'Metro Station', offset: -15 },
];

/**
 * HostelMap Component
 *
 * Displays the location of Inn-Berlin Hostel using an embedded Google Map iframe.
 * Additionally shows animated floating icons highlighting nearby attractions
 * and a detailed text-based information card about the hostel area.
 *
 * Layout:
 * - Left section contains a styled Google Map with floating icons.
 * - Right section provides a visual summary of location benefits.
 *
 * Features:
 * - Uses `Motion.div` from Framer Motion to animate the entrance of each floating icon.
 * - Floating icons use their defined `offset` for vertical animation direction.
 * - Content is wrapped in a responsive container with flex layout for proper alignment.
 *
 * @component
 * @returns {JSX.Element} A full-width section containing a styled map and info content.
 */
const HostelMap = () => {
    return (
        <section className="hostel-map" id="map">
            <div className="map-section-wrapper">
                {/*  Google Map */}
                <div className="map-container">
                    <iframe
                        title="Inn-Berlin Hostel Location"
                        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2425.5492109107604!2d13.383902277541411!3d52.55967857207146!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47a8523b8ef157bf%3A0x533fcb07910320e8!2sHostel%20Inn-Berlin!5e0!3m2!1sen!2sde!4v1743658272668!5m2!1sen!2sde"
                        allowFullScreen=""
                        loading="lazy"
                        referrerPolicy="no-referrer-when-downgrade"
                    ></iframe>

                    {/* ✨ Animated Floating Icons */}
                    <div className="floating-icons">
                        {attractions.map((attr, index) => (
                            <Motion.div
                                className="floating-icon"
                                key={index}
                                initial={{ opacity: 0, y: attr.offset }}
                                whileInView={{ opacity: 1, y: 0 }}
                                transition={{ duration: 0.6, delay: index * 0.2 }}
                                viewport={{ once: true }}
                            >
                                {attr.icon}
                                <span>{attr.label}</span>
                            </Motion.div>
                        ))}
                    </div>
                </div>

                {/* Map Info */}
                <div className="map-info">
                    <h2>📍 In the Heart of Wedding</h2>
                    <p>
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit,
                        sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi
                        ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit
                        in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                        Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia
                        deserunt mollit anim id est laborum."
                    </p>
                    <ul>
                        <li>🏛️ 15 min walk to Museums </li>
                        <li>🌳 2 min walk to City Park</li>
                        <li>🚇 3 min walk to Metro Station</li>
                        <li>☕ Surrounded by cafes, bakeries, and museums</li>
                    </ul>
                </div>
            </div>
        </section>
    );
};

export default HostelMap;