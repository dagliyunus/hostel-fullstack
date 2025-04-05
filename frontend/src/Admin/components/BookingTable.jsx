import React from 'react';
import '../../styles/admin/managementTables/style.css';

/**
 * BookingTable Component
 *
 * Displays a table of all bookings from BookingDTO.
 * Each booking includes key info like ID, customer name/email,
 * room/bed info, status, dates, and price.
 *
 * @component
 * @param {Object[]} bookings - Array of BookingDTO objects to display.
 * @param {function} onAdd
 * @param {function} onEdit - Triggered when the "Edit" button is clicked.
 * @param {function} onDelete - Triggered when the "Delete" button is clicked.
 */
const BookingTable = ({ bookings,onAdd, onEdit, onDelete }) => {
    return (
            <div className="payment-table-wrapper">
                <table className="payment-table">
                    <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Customer Full Name</th>
                        <th>Email</th>
                        <th>Room</th>
                        <th>Bed</th>
                        <th>Status</th>
                        <th>Check-In</th>
                        <th>Check-Out</th>
                        <th>Total (€)</th>
                        <th>Created At</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                        <tbody>
                        {bookings.map((booking) => (
                            <tr key={booking.bookingId}>
                                <td>{booking.bookingId}</td>
                                <td>{booking.customerFullName}</td>
                                <td>{booking.customerEmail}</td>
                                <td>{booking.roomNumber}</td>
                                <td>{booking.bedNumber}</td>
                                <td>{booking.bookingStatus}</td>
                                <td>{booking.checkInDate}</td>
                                <td>{booking.checkOutDate}</td>
                                <td>{booking.totalPrice}</td>
                                <td>{booking.createdAt}</td>
                                <td>
                                    <button onClick={() => onAdd(booking.bookingId)}>Add</button>
                                    <button onClick={() => onEdit(booking.bookingId)}>Edit</button>
                                    <button onClick={() => onDelete(booking.bookingId)}>Delete</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
            </div>
    );
};

export default BookingTable;