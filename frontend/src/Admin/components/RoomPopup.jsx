import React from 'react';

/**
 * RoomPopup component is a modal-style UI element used to display detailed information
 * about a specific room within the admin dashboard. This includes metadata like ID,
 * room number, capacity, and floor, as well as an interactive list of beds assigned
 * to that room.
 *
 * Admins can use this popup to:
 * - Review room details fetched from the backend via the `getRoomWithBeds` endpoint.
 * - View all beds within the room.
 * - Perform bed-level CRUD operations (Add, Update, Delete) using dedicated handler buttons.
 *
 * @param {{
 *   room: {
 *     roomId: string,
 *     roomNumber: string,
 *     capacity: number,
 *     floor: number,
 *     beds: Array<{ bedNumber: string }>
 *   },
 *   onClose: Function,
 *   onAddBedForPopup: Function,
 *   onUpdateBedForPopup: Function,
 *   onDeleteBedForPopup: Function
 * }} props - Props passed down from the ManageRooms component to render the selected room.
 */
const RoomPopup = ({ room, onClose, onAddBedForPopup, onDeleteBedForPopup }) => {
    if (!room) return null;

    return (
        <div className="popup">
            <div className="popup-content">
                <h3>Room Details</h3>
                <p><strong>ID:</strong> {room.roomId}</p>
                <p><strong>Number:</strong> {room.roomNumber}</p>
                <p><strong>Capacity:</strong> {room.capacity}</p>
                <p><strong>Floor:</strong> {room.floor}</p>

                <h4>Beds</h4>
                <ul>
                    {/* Render the beds list */}
                    {room.beds && room.beds.length > 0 ? (
                        room.beds.map((bed, index) => (
                            <li key={index}>Bed Number: {bed.bedNumber}</li>
                        ))
                    ) : (
                        <li>No beds available</li>
                        )}
                </ul>
                <div className="bed-actions">
                    <button onClick={() => onAddBedForPopup(room.roomId)}>Add Bed</button>
                    <button onClick={() => onDeleteBedForPopup(room.roomId)}>Delete Bed</button>
                </div>

                <button onClick={onClose}>Close</button>
            </div>
        </div>
    );
};

export default RoomPopup;