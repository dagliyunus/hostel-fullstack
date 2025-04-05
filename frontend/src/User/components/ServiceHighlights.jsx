import React from 'react';
import '../../styles/global/ServiceHighlights.css';
import { motion as Motion } from 'framer-motion';
import {FaWifi, FaShower, FaCoffee, FaLock, FaAddressBook} from 'react-icons/fa';

const services = [
    { icon: <FaWifi />, label: 'Free Wi-Fi' },
    { icon: <FaShower />, label: 'Laundry' },
    { icon: <FaCoffee />, label: 'Breakfast' },
    { icon: <FaLock />, label: 'Secure Lockers' },
    { icon: <FaAddressBook />, label: 'Anmeldung' },
];

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