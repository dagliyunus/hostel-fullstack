import React, { useState } from 'react';
import '../../styles/global/RoomShowcase.css';
import { motion as Motion } from 'framer-motion';

const rooms = [
    {
        number: 'RN1',
        type: '2-Bed Room',
        capacity: '2 Guests',
        beds: '2 Beds',
        price: 25,
        image: '/public/2-bed.jpeg',
        description: 'Perfect for solo travelers or couples. Offers privacy, comfort, and modern design as well as private bathroom.'
    },
    {
        number: 'RN2',
        type: '4-Bed Room',
        capacity: '4 Guests',
        beds: '4 Beds',
        price: 20,
        image: '/public/4-bed.jpg',
        description: 'Ideal for backpackers or small groups. Each bed has individual lockers and lights. Common room is great for socializing with other travellers. Bathrooms and toilets are shared.'
    },
    {
        number: 'RN3',
        type: '6-Bed Room',
        capacity: '6 Guests',
        beds: '6 Beds',
        price: 15,
        image: '/public/6-bed.jpg',
        description: 'Great for large groups or budget travelers. Spacious and clean with a cozy vibe. Bathrooms and toilets are shared.'
    }
];

const RoomShowcase = () => {
    const [selectedRoom, setSelectedRoom] = useState(null);

    const scrollToBookingSection = (roomNumber) => {
        sessionStorage.setItem("selectedRoom", roomNumber);
        const bookingSection = document.getElementById("booking");
        if (bookingSection) {
            bookingSection.scrollIntoView({ behavior: "smooth" });
        }
    };

    return (
        <section className="room-showcase">
            <h2 className="section-title">Our Rooms</h2>
            <div className="room-list">
                {rooms.map((room, index) => (
                    <Motion.div
                        className="room-card"
                        key={room.number}
                        initial={{ x: index % 2 === 0 ? -100 : 100, opacity: 0 }}
                        whileInView={{ x: 0, opacity: 1 }}
                        viewport={{ once: true }}
                        transition={{ duration: 0.8, ease: 'easeOut' }}
                    >
                        <div className="price-badge">€{room.price}/night</div>

                        <img src={room.image} alt={room.type} className="room-image" />
                        <div className="room-info">
                            <h3>{room.type}</h3>
                            <p>Room No: {room.number}</p>
                            <p>Capacity: {room.capacity}</p>
                            <p>Beds: {room.beds}</p>
                            <div className="button-group">
                                <button className="details-btn" onClick={() => setSelectedRoom(room)}>
                                    View Details
                                </button>
                                <button className="book-btn" onClick={() => scrollToBookingSection(room.number)}>
                                    Book Now!
                                </button>
                            </div>
                        </div>
                    </Motion.div>
                ))}
            </div>

            {/* Modal */}
            {selectedRoom && (
                <div className="modal-overlay" onClick={() => setSelectedRoom(null)}>
                    <div className="modal-card" onClick={e => e.stopPropagation()}>
                        <button className="close-btn" onClick={() => setSelectedRoom(null)}>✖</button>
                        <img src={selectedRoom.image} alt={selectedRoom.type} className="modal-image" />
                        <h2>{selectedRoom.type}</h2>
                        <p className="modal-desc">{selectedRoom.description}</p>
                        <ul>
                            <li><strong>Room No:</strong> {selectedRoom.number}</li>
                            <li><strong>Capacity:</strong> {selectedRoom.capacity}</li>
                            <li><strong>Beds:</strong> {selectedRoom.beds}</li>
                            <li><strong>Price:</strong> €{selectedRoom.price}/night</li>
                        </ul>
                        <button className="book-btn" onClick={() => scrollToBookingSection(selectedRoom.number)}>
                            Book This Room
                        </button>
                    </div>
                </div>
            )}
        </section>
    );
};

export default RoomShowcase;