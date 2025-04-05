import React, { useState, useEffect } from 'react';
import { motion, useAnimation } from 'framer-motion';
import '../../styles/global/BookingSection.css';

const roomPrices = {
    'RN1': 25,
    'RN2': 20,
    'RN3': 15
};

const calculateNights = (checkIn, checkOut) => {
    const inDate = new Date(checkIn);
    const outDate = new Date(checkOut);
    const diff = outDate.getTime() - inDate.getTime();
    return diff > 0 ? Math.ceil(diff / (1000 * 3600 * 24)) : 0;
};

const initialFormState = {
    roomType: '',
    checkIn: '',
    checkOut: '',
    guests: 1,
    firstName: '',
    lastName: '',
    dob: '',
    email: '',
    phone: '',
    paymentMethod: 'credit',
    cardholder: '',
    cardNumber: '',
    expiry: '',
    cvv: ''
};

const BookingSection = ({ searchCriteria }) => {
    const [formData, setFormData] = useState(initialFormState);
    const [confirmation, setConfirmation] = useState(null);
    const [availableRooms, setAvailableRooms] = useState([]);
    const [loadingRooms, setLoadingRooms] = useState(false);
    const [hasSearched, setHasSearched] = useState(false);

    useEffect(() => {
        if (searchCriteria) {
            setFormData(prev => ({
                ...prev,
                checkIn: searchCriteria.checkIn || '',
                checkOut: searchCriteria.checkOut || '',
                guests: searchCriteria.guests || 1
            }));
        }
    }, [searchCriteria]);

    useEffect(() => {
        const fetchAvailableRooms = async () => {
            const { checkIn, checkOut, guests } = searchCriteria || {};

            console.log('🔎 Triggered fetchAvailableRooms');
            console.log('🗓️ checkIn:', checkIn, '| checkOut:', checkOut, '| guests:', guests);

            if (!checkIn || !checkOut || guests < 1) {
                console.log('⛔ Missing data - aborting fetch.');
                return;
            }

            setHasSearched(true);
            setLoadingRooms(true);
            try {
                const res = await fetch(
                    `http://localhost:8080/api/user/rooms/available?checkIn=${checkIn}&checkOut=${checkOut}&guests=${guests}`
                );

                if (!res.ok) {
                    const errorText = await res.text();
                    console.error('❌ Fetch failed:', errorText);
                    setAvailableRooms([]);
                    return;
                }

                const data = await res.json();
                console.log('✅ Available rooms from backend:', data);
                setAvailableRooms(data);

                if (data.length > 0 && !formData.roomType) {
                    setFormData(prev => {
                        const updated = { ...prev, roomType: data[0] };
                        console.log('📝 Auto-selected roomType:', updated.roomType);
                        return updated;
                    });
                }
            } catch (error) {
                console.error('🚨 Error fetching rooms:', error);
                setAvailableRooms([]);
            } finally {
                setLoadingRooms(false);
            }
        };

        fetchAvailableRooms();
    }, [searchCriteria]);

    const nights = calculateNights(formData.checkIn, formData.checkOut);
    const pricePerNight = roomPrices[formData.roomType] || 0;
    const total = nights * pricePerNight * formData.guests;

    const totalControls = useAnimation();

    useEffect(() => {
        if (nights > 0 && roomPrices[formData.roomType]) {
            totalControls.start({
                scale: [1, 1.05, 1],
                transition: { duration: 0.5, ease: 'easeInOut' }
            });
        }
    }, [nights, formData.roomType, totalControls]);

    useEffect(() => {
        const selectedRoom = sessionStorage.getItem("selectedRoom");

        setFormData(prev => ({
            ...prev,
            checkIn: searchCriteria?.checkIn || '',
            checkOut: searchCriteria?.checkOut || '',
            guests: searchCriteria?.guests || 1,
            roomType: selectedRoom || ''
        }));

        if (selectedRoom) sessionStorage.removeItem("selectedRoom");
    }, [searchCriteria]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            customerFirstName: formData.firstName,
            customerLastName: formData.lastName,
            customerEmail: formData.email,
            customerPhone: formData.phone,
            customerDateOfBirth: formData.dob,
            roomNumber: formData.roomType,
            checkInDate: formData.checkIn,
            checkOutDate: formData.checkOut,
            totalPrice: total
        };

        try {
            const response = await fetch("http://localhost:8080/api/user/bookings/createBooking", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                const data = await response.json();
                setConfirmation(data);
                setFormData(initialFormState);

                // 👇 NEW: Trigger SMS sending
                await fetch("http://localhost:8080/api/contact/send-sms", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        name: `${payload.customerFirstName} ${payload.customerLastName}`,
                        email: payload.customerEmail,
                        message: `🛏️ New Booking:\nRoom: ${payload.roomNumber}\nGuests: ${formData.guests}\nCheck-in: ${payload.checkInDate}\nCheck-out: ${payload.checkOutDate}\nTotal: €${payload.totalPrice}`
                    })
                });
            } else {
                const errorData = await response.text();
                alert("Booking failed: " + errorData);
            }
        } catch (error) {
            console.error("Error creating booking:", error);
            alert("An error occurred. Please try again.");
        }
    };

    return (
        <section className="booking-section interactive-background" id="booking">
            <h2 className="section-title modern-font">Book Your Stay</h2>

            <div className="booking-layout">

                {hasSearched && (
                <div className="available-rooms-section">
                    <h3>Available Rooms</h3>
                    {loadingRooms ? (
                        <p>🔄 Checking availability...</p>
                    ) : availableRooms.length > 0 ? (
                        <motion.ul
                            className="available-rooms-list"
                            initial={{ opacity: 0, y: 10 }}
                            animate={{ opacity: 1, y: 0 }}
                            transition={{ duration: 0.4, ease: 'easeOut' }}
                        >
                            {availableRooms.map(room => (
                                <motion.li
                                    key={room}
                                    onClick={() => setFormData(prev => ({ ...prev, roomType: room }))}
                                    className={formData.roomType === room ? 'selected-room' : ''}
                                    whileHover={{ scale: 1.02 }}
                                    whileTap={{ scale: 0.97 }}
                                >
                                    ✅ {room === 'RN1' ? '2-Bed Room' : room === 'RN2' ? '4-Bed Room' : room === 'RN3' ? '6-Bed Room' : room}
                                </motion.li>
                            ))}
                        </motion.ul>
                    ) : (
                        <p>⚠️ No rooms available for the selected dates and guest count.</p>
                    )}
                </div>
                )}
                <motion.form
                    className="booking-form-box"
                    onSubmit={handleSubmit}
                    initial={{ opacity: 0, y: 40 }}
                    whileInView={{ opacity: 1, y: 0 }}
                    transition={{ duration: 0.8, ease: 'easeOut' }}
                    viewport={{ once: true }}
                >
                    {/* Room Type */}
                    <div className="room-type-row">
                        <select name="roomType" value={formData.roomType} onChange={handleChange} required>
                            <option value="">Select Room Type</option>
                            <option value="RN1">2-Bed Room</option>
                            <option value="RN2">4-Bed Room</option>
                            <option value="RN3">6-Bed Room</option>
                        </select>
                    </div>

                    {/* Dates & Guests */}
                    <div className="booking-dates">
                        <input type="date" name="checkIn" value={formData.checkIn} onChange={handleChange} required />
                        <input type="date" name="checkOut" value={formData.checkOut} onChange={handleChange} required />
                        <input type="number" name="guests" min="1" value={formData.guests} onChange={handleChange} required />
                    </div>

                    <hr />

                    {/* Personal Info */}
                    <div className="personal-info">
                        <input type="text" name="firstName" placeholder="First Name" value={formData.firstName} onChange={handleChange} required />
                        <input type="text" name="lastName" placeholder="Last Name" value={formData.lastName} onChange={handleChange} required />
                        <input type="date" name="dob" placeholder="Date of Birth" value={formData.dob} onChange={handleChange} required />
                        <input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} required />
                        <input type="text" name="phone" placeholder="Phone Number" value={formData.phone} onChange={handleChange} required />
                    </div>

                    <hr />

                    {/* Payment Method */}
                    <div className="payment-methods-styled">
                        {['credit', 'paypal', 'applepay', 'googlepay'].map(method => (
                            <motion.label
                                whileHover={{ scale: 1.02 }}
                                whileTap={{ scale: 0.98 }}
                                key={method}
                                className={formData.paymentMethod === method ? 'active' : 'disabled'}
                            >
                                <input
                                    type="radio"
                                    name="paymentMethod"
                                    value={method}
                                    checked={formData.paymentMethod === method}
                                    onChange={handleChange}
                                    disabled={method !== 'credit'}
                                />
                                {method === 'credit' && '💳 Credit/Debit Card'}
                                {method === 'paypal' && '🅿️ PayPal'}
                                {method === 'applepay' && '🍏 Apple Pay'}
                                {method === 'googlepay' && '🅶 Google Pay'}
                            </motion.label>
                        ))}
                    </div>

                    <hr />

                    {/* Card Details */}
                    <div className="payment-fields">
                        <div className="payment-row">
                            <input type="text" name="cardholder" placeholder="Cardholder Name" value={formData.cardholder} onChange={handleChange} required />
                            <input type="text" name="cardNumber" placeholder="Card Number" value={formData.cardNumber} onChange={handleChange} maxLength="16" required />
                        </div>
                        <div className="payment-row">
                            <input type="text" name="expiry" placeholder="MM/YY" value={formData.expiry} onChange={handleChange} maxLength="5" required />
                            <input type="text" name="cvv" placeholder="CVV" value={formData.cvv} onChange={handleChange} maxLength="4" required />
                        </div>
                    </div>

                    {/* Total Display */}
                    {nights > 0 && pricePerNight > 0 && (
                        <motion.div className="total-price" animate={totalControls}>
                            Total ({nights} night{nights > 1 ? 's' : ''} × {formData.guests} guest{formData.guests > 1 ? 's' : ''} @ €{pricePerNight}/night) :
                            <strong> €{total}.00</strong>
                        </motion.div>
                    )}

                    <button type="submit">Confirm & Pay</button>
                </motion.form>

                {/* Booking Summary */}
                <motion.div
                    className="summary-box"
                    initial={{ opacity: 0, x: 30 }}
                    whileInView={{ opacity: 1, x: 0 }}
                    transition={{ duration: 0.6, ease: 'easeOut' }}
                    viewport={{ once: true }}
                >
                    <h4>Booking Summary</h4>
                    <p><strong>Room:</strong> {
                        formData.roomType === 'RN1' ? '2-Bed Room' :
                            formData.roomType === 'RN2' ? '4-Bed Room' :
                                formData.roomType === 'RN3' ? '6-Bed Room' : '-'
                    }</p>
                    <p><strong>Guests:</strong> {formData.guests}</p>
                    <p><strong>Check-in:</strong> {formData.checkIn || '-'}</p>
                    <p><strong>Check-out:</strong> {formData.checkOut || '-'}</p>
                    <p><strong>Total Price:</strong> {total > 0 ? `€${total.toFixed(2)}` : '-'}</p>
                </motion.div>

                {/* Confirmation Panel */}
                {confirmation && (
                    <motion.div
                        className="confirmation-panel"
                        initial={{ opacity: 0, y: 20 }}
                        animate={{ opacity: 1, y: 0 }}
                        transition={{ duration: 0.5, ease: 'easeOut' }}
                    >
                        <h3>🎉 Booking Confirmed!</h3>
                        <p><strong>Booking ID:</strong> {confirmation.bookingId}</p>
                        <p><strong>Name:</strong> {confirmation.customerFullName}</p>
                        <p><strong>Room Number:</strong> {confirmation.roomNumber}</p>
                        <p><strong>Check-in:</strong> {confirmation.checkInDate}</p>
                        <p><strong>Check-out:</strong> {confirmation.checkOutDate}</p>
                        <p><strong>Total Price:</strong> €{confirmation.totalPrice}</p>
                        <p><strong>Payment ID:</strong> {confirmation.paymentId}</p>
                        <p><em>🧾 An invoice has been sent to your email address.</em></p>
                    </motion.div>
                )}
            </div>
        </section>
    );
};

export default BookingSection;