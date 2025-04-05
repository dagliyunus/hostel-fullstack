import React, {useState} from 'react';
import HeroSection from '../components/HeroSection';
import RoomShowcase from '../components/RoomShowcase';
import ServiceHighlights from '../components/ServiceHighlights';
import HostelMap from '../components/HostelMap';
import BookingSection from '../components/BookingSection';
import GalleryTestimonials from './GalleryTestimonials.jsx';
import Footer from '../components/Footer';
import '../../styles/global/HomePage.css';
import ContactForm from "./ContactForm.jsx";
import Navbar from "./Navbar.jsx";

/**
 * HomePage Component
 *
 * Serves as the main landing page for the Inn-Berlin Hostel website.
 * It organizes all major UI sections such as hero, rooms, services, booking,
 * map, gallery/testimonials, contact, and footer.
 *
 * State:
 * @state {Object} searchCriteria - Stores booking form search input values.
 * @property {string} checkIn - Selected check-in date.
 * @property {string} checkOut - Selected check-out date.
 * @property {number} guests - Number of guests.
 *
 * Component Structure:
 * - <Navbar />: Sticky sidebar navigation for section jump links.
 * - <HeroSection />: Hero banner with headline, subtitle, and date/guest search form.
 *    - Receives `setSearchCriteria` prop to update state in parent.
 * - <RoomShowcase />: Displays featured room cards with info and image.
 * - <ServiceHighlights />: Highlights hostel amenities and services.
 * - <HostelMap />: Displays location map with contact/info details.
 * - <BookingSection />: Booking form and availability, accepts `searchCriteria` as prop.
 * - <GalleryTestimonials />: Auto-scroll gallery + guest feedback.
 * - <ContactForm />: Simple form for inquiries.
 * - <Footer />: Legal and contact links at bottom of the page.
 *
 * Behavior:
 * - State is initialized with default guest count and empty dates.
 * - User actions on HeroSection update the state via `setSearchCriteria`.
 * - Updated state is passed to BookingSection to trigger data fetches.
 *
 * @component
 * @returns {JSX.Element} The full homepage layout for the hostel website.
 */
const HomePage = () => {
    const [searchCriteria, setSearchCriteria] = useState({
        checkIn: '',
        checkOut: '',
        guests: 1
    });

    return (
        <div className="homepage-container">
            <Navbar />
            <section id="hero">
                <HeroSection setSearchCriteria={setSearchCriteria} />
            </section>
            <section id="rooms"> <RoomShowcase /> </section>
            <section id="services"> <ServiceHighlights /> </section>
            <section id="map"> <HostelMap /> </section>
            <section id="booking">
                <BookingSection searchCriteria={searchCriteria} />
            </section>
            <section id="gallery"> <GalleryTestimonials /> </section>
            <section id="contact"> <ContactForm /> </section>
            <Footer />
        </div>
    );
};

export default HomePage;
