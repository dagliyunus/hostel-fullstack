import React from 'react';

/**
 * RoomPopup component displays a modal window with details about a specific room,
 * including its properties and associated beds. It allows adding or deleting beds from the room.
 *
 * @component
 * @param {Object} props - Component props.
 * @param {Object} props.room - Room object containing details to display.
 * @param {string} props.room.roomId - Unique identifier of the room.
 * @param {string|number} props.room.roomNumber - Number of the room.
 * @param {number} props.room.capacity - Capacity of the room.
 * @param {number} props.room.floor - Floor number where the room is located.
 * @param {Array<Object>} [props.room.beds] - Optional list of beds in the room.
 * @param {string|number} props.room.beds[].bedNumber - Number assigned to a specific bed.
 * @param {function} props.onClose - Callback to close the popup.
 * @param {function} props.onAddBedForPopup - Callback triggered to add a bed to the room.
 * @param {function} props.onDeleteBedForPopup - Callback triggered to delete a bed from the room.
 * @returns {JSX.Element|null} A JSX modal element displaying the room details or null if no room is provided.
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