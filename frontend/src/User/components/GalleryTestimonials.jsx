import React from 'react';
import '../../styles/global/GalleryTestimonials.css';
import { motion as Motion } from 'framer-motion';

const galleryImages = [
    '/public/reservation.jpg',
    '/public/common.jpg',
    '/public/comroom.jpg',
    '/public/kitchen.jpg',
];

const testimonials = [
    {
        name: 'Lina M.',
        text: 'One of the coziest hostels I’ve ever stayed in. Great vibes and lovely staff!',
    },
    {
        name: 'Alex R.',
        text: 'Perfect location, super clean rooms, and the Wi-Fi was fast. Highly recommend!',
    },
];

const GalleryTestimonials = () => {
    return (
        <section className="gallery-testimonials">
            {/* Auto-scrolling gallery */}
            <div className="gallery">
                <h2 className="section-title">Gallery</h2>
                <div className="gallery-carousel">
                    <div className="gallery-track">
                        {[...galleryImages, ...galleryImages].map((src, idx) => (
                            <img
                                src={src}
                                alt={`Gallery ${idx + 1}`}
                                key={idx}
                                className="gallery-image"
                            />
                        ))}
                    </div>
                </div>
            </div>

            {/* Testimonials */}
            <div className="testimonials">
                <h2 className="section-title">What Our Guests Say</h2>
                <div className="testimonial-list">
                    {testimonials.map((review, idx) => (
                        <Motion.div
                            className="testimonial-card"
                            key={idx}
                            initial={{ opacity: 0, y: 30 }}
                            whileInView={{ opacity: 1, y: 0 }}
                            viewport={{ once: true }}
                            transition={{ duration: 0.5, delay: idx * 0.15 }}
                        >
                            <p className="testimonial-text">“{review.text}”</p>
                            <p className="testimonial-name">— {review.name}</p>
                        </Motion.div>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default GalleryTestimonials;