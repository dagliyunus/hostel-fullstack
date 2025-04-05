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
