import React from 'react';
import '../../styles/global/Footer.css';
import { FaInstagram, FaFacebookF, FaTwitter,  FaUserShield } from 'react-icons/fa';

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