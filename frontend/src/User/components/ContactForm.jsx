// src/User/components/ContactForm.jsx
import React, { useState } from 'react';
import axios from 'axios';
import '../../styles/global/ContactForm.css';

const ContactForm = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        message: ''
    });

    const [submitted, setSubmitted] = useState(false);
    const [status, setStatus] = useState('');

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setStatus('Sending...');

        try {
            await axios.post('http://localhost:8080/api/contact/send-email', formData);
            setSubmitted(true);
            setFormData({ name: '', email: '', message: '' });
            setStatus('✅ Message sent successfully!');
        } catch (error) {
            console.error('❌ Contact form error:', error);
            setStatus('❌ Failed to send message. Please try again.');
        }
    };

    return (
        <div className="contact-form-container">
            <h2>📬 Contact Us</h2>
            {submitted ? (
                <p className="success-message">Thanks for reaching out. We'll get back to you soon!</p>
            ) : (
                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        name="name"
                        placeholder="Your Name"
                        required
                        value={formData.name}
                        onChange={handleChange}
                    />
                    <input
                        type="email"
                        name="email"
                        placeholder="Your Email"
                        required
                        value={formData.email}
                        onChange={handleChange}
                    />
                    <textarea
                        name="message"
                        placeholder="Your Message"
                        required
                        rows="5"
                        value={formData.message}
                        onChange={handleChange}
                    />
                    <button type="submit">Send Message</button>
                    {status && <p className="status-text">{status}</p>}
                </form>
            )}
        </div>
    );
};

export default ContactForm;