import React from 'react';
import '../../styles/admin/managementTables/style.css';

/**
 * RoomTable component renders a table listing all rooms with actions to manage each room.
 *
 * @component
 * @param {Object} props - Component props.
 * @param {Array<Object>} props.rooms - Array of room objects to be displayed.
 * @param {string} props.rooms[].roomId - Unique identifier for the room.
 * @param {string|number} props.rooms[].roomNumber - Room number.
 * @param {number} props.rooms[].capacity - The number of people the room can accommodate.
 * @param {number} props.rooms[].floor - The floor on which the room is located.
 * @param {function} props.onView - Callback function triggered when the "View" button is clicked.
 * @param {function} props.onEdit - Callback function triggered when the "Edit" button is clicked.
 * @param {function} props.onDelete - Callback function triggered when the "Delete" button is clicked.
 * @returns {JSX.Element} A JSX element that renders a table of rooms with available actions.
 */
const RoomTable = ({ rooms, onView, onEdit, onDelete }) => {
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