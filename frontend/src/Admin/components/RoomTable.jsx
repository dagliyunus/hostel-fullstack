import React from 'react';
import '../../styles/admin/managementTables/style.css';

/**
 * RoomTable Component
 *
 * Displays a dynamic HTML table of all hostel rooms provided via props.
 * Each room is rendered as a row containing key attributes like ID, number,
 * capacity, and floor. A set of action buttons is available per room to support
 * future CRUD operations from the admin dashboard.
 *
 * @component
 * @param {Object[]} rooms - Array of room objects to be displayed.
 * @param {function} onView - Callback fired when the "View" button is clicked for a room.
 * @param {function} onEdit - Callback fired when the "Edit" button is clicked for a room.
 * @param {function} onDelete - Callback fired when the "Delete" button is clicked for a room.
 * @param {function} onAdd - Callback fired when the "Add" button is clicked for a room.
 */
const RoomTable = ({ rooms, onView, onEdit, onDelete, onAdd }) => {
    return (
        <div>
            <div className="payment-table-wrapper">
                <table className="payment-table">
                <thead>
                <tr>
                    <th>Room ID</th>
                    <th>Room Number</th>
                    <th>Capacity</th>
                    <th>Floor</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {rooms.map((room) => (
                    <tr key={room.roomId}>
                        <td>{room.roomId}</td>
                        <td>{room.roomNumber}</td>
                        <td>{room.capacity}</td>
                        <td>{room.floor}</td>
                        <td>
                            <button onClick={() => onAdd(room.roomId)}>Add</button>
                            <button onClick={() => onView(room.roomId)}>View</button>
                            <button onClick={() => onEdit(room.roomId)}>Edit</button>
                            <button onClick={() => onDelete(room.roomId)}>Delete</button>
                        </td>

                    </tr>
                ))}
                </tbody>
            </table>
            </div>
        </div>
    );
};

export default RoomTable;