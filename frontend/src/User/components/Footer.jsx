import React from 'react';
import '../../styles/global/Footer.css';
import { FaInstagram, FaFacebookF, FaTwitter,  FaUserShield } from 'react-icons/fa';

/**
 * Footer component for the application.
 *
 * This component renders the website's footer section, which includes:
 * - Contact information (email and phone)
 * - Social media links (Instagram, Facebook, Twitter)
 * - Legal links (Privacy Policy and Terms of Service)
 * - Copyright
 * - Admin login access icon
 *
 * Uses font icons from `react-icons` (e.g., `FaInstagram`, `FaFacebookF`, `FaTwitter`, `FaUserShield`)
 * and is styled via external CSS classes (`footer`, `footer-columns`, etc.).
 *
 * @component
 * @returns {JSX.Element} Rendered footer element.
 */
const Footer = () => {
    return (
        <footer className="footer">
            <div className="footer-columns">
                <div className="footer-section">
                    <h4>Contact</h4>
                    <p>Email: hello@cozystayhostel.com</p>
                    <p>Phone: +49 123 456 789</p>
                </div>

                <div className="footer-section">
                    <h4>Follow Us</h4>
                    <div className="social-icons">
                        <a href="#"><FaInstagram /></a>
                        <a href="#"><FaFacebookF /></a>
                        <a href="#"><FaTwitter /></a>
                    </div>
                </div>

                <div className="footer-section">
                    <h4>Legal</h4>
                    <p><a href="#">Privacy Policy</a></p>
                    <p><a href="#">Terms of Service</a></p>
                </div>
            </div>

            <div className="footer-bottom">
                <p>&copy; {new Date().getFullYear()} Inn-Berlin Hostel. All rights reserved.</p>
                <p>&copy; {new Date().getFullYear()} Developed by Yunus Emre Dagli.</p>
                <a href="/admin/login" className="admin-login" title="Admin Area"><FaUserShield /></a>
            </div>
        </footer>
    );
};

export default Footer;