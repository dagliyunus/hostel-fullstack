import React, { useEffect, useState } from 'react';
import BookingTable from '../../components/BookingTable.jsx';
import {
    getAllBookings,
    createBooking,
    updateBooking,
    deleteBooking
} from '../../services/AdminServices/bookingService.js';

const ManageBookings = () => {
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showAddPopup, setShowAddPopup] = useState(false);
    const [showEditPopup, setShowEditPopup] = useState(false);
    const [newBooking, setNewBooking] = useState({
        customerFirstName: '',
        customerLastName: '',
        customerEmail: '',       // ✅ Add
        customerPhone: '',       // ✅ Add
        customerDateOfBirth: '', // ✅ Add (use type="date")
        roomNumber: '',
        checkInDate: '',
        checkOutDate: '',
        totalPrice: ''
    });
    const [editingBooking, setEditingBooking] = useState(null);

    useEffect(() => {
        const fetchBookings = async () => {
            try {
                const response = await getAllBookings();
                console.log("Full Booking API response:", response.data);      // 👀
                console.log("Booking response data:", response.data);     // 👀
                const data = Array.isArray(response.data) ? response.data : [];
                setBookings(data);
            } catch (error) {
                console.error('Error fetching bookings:', error);
                setBookings([]);
            } finally {
                setLoading(false);
            }
        };
        fetchBookings();
    }, []);


    const onAdd = async () => {
        setShowAddPopup(true);
    };

    const handleAddBooking = async () => {
        const {
            customerFirstName,
            customerLastName,
            customerEmail,
            customerPhone,
            customerDateOfBirth,
            roomNumber,
            checkInDate,
            checkOutDate,
            totalPrice
        } = newBooking;

        const bookingPayload = {
            customerFirstName,
            customerLastName,
            customerEmail,
            customerPhone,
            customerDateOfBirth,
            roomNumber,
            checkInDate,
            checkOutDate,
            totalPrice
        };

        console.log("📦 Cleaned bookingPayload:", bookingPayload);

        try {
            await createBooking(bookingPayload);
            alert('Booking created successfully.');
            setShowAddPopup(false);
            window.location.reload();
        } catch (error) {
            alert('Failed to create booking.');
            console.error(error);
        }
    };

    const handleEdit = async (bookingId) => {
        if (showEditPopup && editingBooking) {
            try {
                await updateBooking(editingBooking);
                alert('Booking updated successfully.');
                setShowEditPopup(false);
                window.location.reload();
            } catch (error) {
                alert('Failed to update booking.');
                console.error(error);
            }
        } else {
            const bookingToEdit = bookings.find(b => b.bookingId === bookingId);
            if (bookingToEdit) {
                setEditingBooking({ ...bookingToEdit });
                setShowEditPopup(true);
            }
        }
    };

    const handleDelete = async (bookingId) => {
        try {
            await deleteBooking(bookingId);
            alert('Booking deleted successfully.');
            window.location.reload();
        } catch (error) {
            alert('Failed to delete booking.');
            console.error(error);
        }
    };

    return (
        <div className="payment-wrapper">
            <div className="payment-header">
                <h2>Manage Bookings</h2>
                <button onClick={() => setShowAddPopup(true)}>Add Booking</button>
            </div>

            {loading ? (
                <p>Loading bookings...</p>
            ) : (
                    <BookingTable
                        bookings={bookings}
                        onAdd={onAdd}
                        onEdit={handleEdit}
                        onDelete={handleDelete}
                    />
            )}

            {/* ➕ Add Booking Popup */}
            {showAddPopup && (
                <div className="popup">
                    <div className="popup-content">
                        <h3>Add New Booking</h3>

                        <input
                            placeholder="Customer First Name"
                            value={newBooking.customerFirstName}
                            onChange={e => setNewBooking({ ...newBooking, customerFirstName: e.target.value })}
                        />
                        <input
                            placeholder="Customer Last Name"
                            value={newBooking.customerLastName}
                            onChange={e => setNewBooking({ ...newBooking, customerLastName: e.target.value })}
                        />
                        <input
                            placeholder="Customer Email"
                            value={newBooking.customerEmail}
                            onChange={e => setNewBooking({ ...newBooking, customerEmail: e.target.value })}
                        />
                        <input
                            placeholder="Customer Phone"
                            value={newBooking.customerPhone}
                            onChange={e => setNewBooking({ ...newBooking, customerPhone: e.target.value })}
                        />
                        <input
                            type="date"
                            placeholder="Date of Birth"
                            value={newBooking.customerDateOfBirth}
                            onChange={e => setNewBooking({ ...newBooking, customerDateOfBirth: e.target.value })}
                        />
                        <input
                            placeholder="Room Number (e.g. R32)"
                            value={newBooking.roomNumber}
                            onChange={e => setNewBooking({ ...newBooking, roomNumber: e.target.value })}
                        />
                        <input
                            type="date"
                            placeholder="Check-In Date"
                            value={newBooking.checkInDate}
                            onChange={e => setNewBooking({ ...newBooking, checkInDate: e.target.value })}
                        />
                        <input
                            type="date"
                            placeholder="Check-Out Date"
                            value={newBooking.checkOutDate}
                            onChange={e => setNewBooking({ ...newBooking, checkOutDate: e.target.value })}
                        />
                        <input
                            type="number"
                            placeholder="Total Price (€)"
                            value={newBooking.totalPrice}
                            onChange={e => setNewBooking({ ...newBooking, totalPrice: e.target.value })}
                        />

                        <button onClick={handleAddBooking}>Submit</button>
                        <button onClick={() => setShowAddPopup(false)}>Cancel</button>
                    </div>
                </div>
            )}

            {/* ✏️ Edit Booking Popup */}
            {showEditPopup && editingBooking && (
                <div className="popup">
                    <div className="popup-content">
                        <h3>Edit Booking</h3>
                        <input
                            type="date"
                            placeholder="Check-In Date"
                            value={editingBooking.checkInDate}
                            onChange={e => setEditingBooking({ ...editingBooking, checkInDate: e.target.value })}
                        />
                        <input
                            type="date"
                            placeholder="Check-Out Date"
                            value={editingBooking.checkOutDate}
                            onChange={e => setEditingBooking({ ...editingBooking, checkOutDate: e.target.value })}
                        />
                        <button onClick={() => handleEdit(editingBooking.bookingId)}>Update</button>
                        <button onClick={() => setShowEditPopup(false)}>Cancel</button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ManageBookings;