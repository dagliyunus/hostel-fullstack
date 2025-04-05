import React from 'react';
import '../../styles/admin/managementTables/style.css';

/**
 * BookingTable component renders a table view of all booking records.
 *
 * @component
 * @param {Object} props - Component props.
 * @param {Array<Object>} props.bookings - An array of booking objects to display.
 * @param {string} props.bookings[].bookingId - Unique identifier for the booking.
 * @param {string} props.bookings[].customerFullName - Full name of the customer.
 * @param {string} props.bookings[].customerEmail - Email address of the customer.
 * @param {string|number} props.bookings[].roomNumber - The room number assigned.
 * @param {string|number} props.bookings[].bedNumber - The bed number assigned.
 * @param {string} props.bookings[].bookingStatus - The current status of the booking (e.g., confirmed, cancelled).
 * @param {string} props.bookings[].checkInDate - Date of check-in.
 * @param {string} props.bookings[].checkOutDate - Date of check-out.
 * @param {number} props.bookings[].totalPrice - Total price of the booking in Euros.
 * @param {string} props.bookings[].createdAt - Timestamp of when the booking was created.
 * @param {function} props.onAdd - Callback function triggered when the "Add" button is clicked.
 * @param {function} props.onEdit - Callback function triggered when the "Edit" button is clicked.
 * @param {function} props.onDelete - Callback function triggered when the "Delete" button is clicked.
 * @returns {JSX.Element} A JSX element that renders the bookings in a table format with action buttons.
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