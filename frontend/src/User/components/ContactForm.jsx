// src/User/components/ContactForm.jsx
import React, { useState } from 'react';
import axios from 'axios';
import '../../styles/global/ContactForm.css';

/**
 * ContactForm component for user inquiries and feedback.
 *
 * Allows users to submit their name, email, and a message. Upon submission,
 * the data is sent to a backend API endpoint (`/api/contact/send-email`) via POST request.
 * Displays success or failure messages based on the response.
 *
 * @component
 * @returns {JSX.Element} Rendered contact form UI.
 */
const ContactForm = () => {
    /**
     * State hook to manage form input fields.
     *
     * @type {[{ name: string, email: string, message: string }, Function]}
     */
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        message: ''
    });

    /**
     * Tracks whether the form has been successfully submitted.
     *
     * @type {[boolean, Function]}
     */
    const [submitted, setSubmitted] = useState(false);
    /**
     * Tracks the status message for the form submission (e.g., success/failure/loading).
     *
     * @type {[string, Function]}
     */
    const [status, setStatus] = useState('');

    /**
     * Handles user input changes for the form fields.
     *
     * Dynamically updates `formData` based on input `name` and `value`.
     *
     * @param {React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>} e - The event object from input/textarea.
     */
    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    /**
     * Handles the form submission event.
     *
     * Sends the form data to the backend API. On success, resets the form
     * and shows a success message. On failure, shows an error status.
     *
     * @param {React.FormEvent<HTMLFormElement>} e - The form submission event.
     * @async
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        setStatus('Sending...');

        try {
            await axios.post('http://localhost:8080/api/contact/send-email', formData);
            setSubmitted(true);
            setFormData({ name: '', email: '', message: '' });
            setStatus(' Message sent successfully!');
        } catch (error) {
            console.error(' Contact form error:', error);
            setStatus(' Failed to send message. Please try again.');
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